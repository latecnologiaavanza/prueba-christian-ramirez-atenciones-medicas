package com.insalud.atencionesmedicas.security;

import com.insalud.atencionesmedicas.model.Empleado;
import com.insalud.atencionesmedicas.model.Persona;
import com.insalud.atencionesmedicas.model.Usuario;
import com.insalud.atencionesmedicas.model.enums.Rol;
import com.insalud.atencionesmedicas.repository.EmpleadoRepository;
import com.insalud.atencionesmedicas.repository.PacienteRepository;
import com.insalud.atencionesmedicas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Obtenemos la persona asociada al usuario
        Persona persona = usuario.getPersona();

        // Determinamos el rol según si es paciente o empleado
        Rol rol;
        if (pacienteRepository.existsByPersona(persona)) {
            rol = Rol.PACIENTE;
        } else {
            Empleado empleado = empleadoRepository.findByPersona(persona)
                    .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));
            rol = empleado.getRol();
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + rol.name())
        );

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsuario(),
                usuario.getContrasena(),
                authorities
        );
    }
}
