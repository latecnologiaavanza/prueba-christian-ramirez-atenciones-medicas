package com.insalud.atencionesmedicas.repository;

import com.insalud.atencionesmedicas.model.Especialidad;
import com.insalud.atencionesmedicas.model.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    Page<Especialidad> findByEstado(Estado estado, Pageable pageable);

    @Query("SELECT e FROM Especialidad e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Especialidad> searchByNombre(@Param("nombre") String nombre, Pageable pageable);
}
