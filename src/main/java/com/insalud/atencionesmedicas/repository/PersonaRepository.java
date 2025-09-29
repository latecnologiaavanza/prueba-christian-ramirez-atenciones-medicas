package com.insalud.atencionesmedicas.repository;

import com.insalud.atencionesmedicas.model.Persona;
import com.insalud.atencionesmedicas.model.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByEmail(String email);

    Page<Persona> findByEstado(Estado estado, Pageable pageable);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Persona> searchByNombre(@Param("nombre") String nombre, Pageable pageable);
}
