package com.insalud.atencionesmedicas.repository;

import com.insalud.atencionesmedicas.model.Usuario;
import com.insalud.atencionesmedicas.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    Optional<Usuario> findByPersona(Persona persona);

    boolean existsByUsuario(String usuario);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.usuario) LIKE LOWER(CONCAT('%', :usuario, '%'))")
    Page<Usuario> searchByUsuario(@Param("usuario") String usuario, Pageable pageable);
}
