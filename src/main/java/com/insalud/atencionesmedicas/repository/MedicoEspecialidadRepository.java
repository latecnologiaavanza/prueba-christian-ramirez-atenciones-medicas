package com.insalud.atencionesmedicas.repository;

import com.insalud.atencionesmedicas.model.MedicoEspecialidad;
import com.insalud.atencionesmedicas.model.Empleado;
import com.insalud.atencionesmedicas.model.Especialidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoEspecialidadRepository extends JpaRepository<MedicoEspecialidad, Long> {

    Page<MedicoEspecialidad> findByEmpleado(Empleado empleado, Pageable pageable);

    Page<MedicoEspecialidad> findByEspecialidad(Especialidad especialidad, Pageable pageable);
}
