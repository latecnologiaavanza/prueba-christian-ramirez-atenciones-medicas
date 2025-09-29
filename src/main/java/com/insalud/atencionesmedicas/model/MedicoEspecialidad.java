package com.insalud.atencionesmedicas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medico_especialidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_medico_especialidad_empleado"))
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_medico_especialidad_especialidad"))
    private Especialidad especialidad;
}
