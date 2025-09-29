package com.insalud.atencionesmedicas.service.impl;

import com.insalud.atencionesmedicas.dto.request.AuthLoginRequestDTO;
import com.insalud.atencionesmedicas.dto.request.AuthRegisterRequestDTO;
import com.insalud.atencionesmedicas.dto.response.AuthResponseDTO;
import com.insalud.atencionesmedicas.dto.response.MessageResponseDTO;
import com.insalud.atencionesmedicas.model.Empleado;
import com.insalud.atencionesmedicas.model.Persona;
import com.insalud.atencionesmedicas.model.Usuario;
import com.insalud.atencionesmedicas.model.enums.Rol;
import com.insalud.atencionesmedicas.repository.EmpleadoRepository;
import com.insalud.atencionesmedicas.repository.PacienteRepository;
import com.insalud.atencionesmedicas.repository.PersonaRepository;
import com.insalud.atencionesmedicas.repository.UsuarioRepository;
import com.insalud.atencionesmedicas.security.JwtUtil;
import com.insalud.atencionesmedicas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final PacienteRepository pacienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public MessageResponseDTO registrar(AuthRegisterRequestDTO request) {
        if (usuarioRepository.existsByUsuario(request.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }

        Persona persona = personaRepository.findById(request.getPersonaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Persona no encontrada"));

        Usuario usuario = Usuario.builder()
                .usuario(request.getUsuario())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .persona(persona)
                .build();

        usuarioRepository.save(usuario);

        return new MessageResponseDTO("Usuario registrado correctamente");
    }


    @Override
    public AuthResponseDTO login(AuthLoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getContrasena())
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));

        Persona persona = usuario.getPersona();

        Rol rol;
        if (pacienteRepository.existsByPersona(persona)) {
            rol = Rol.PACIENTE;
        } else {
            Empleado empleado = empleadoRepository.findByPersona(persona)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empleado no encontrado"));
            rol = empleado.getRol();
        }

        String token = jwtUtil.generarToken(usuario.getUsuario(), rol);
        return new AuthResponseDTO(token, "Bearer");
    }
}
