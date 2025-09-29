package com.insalud.atencionesmedicas.model;

import com.insalud.atencionesmedicas.model.enums.Estado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "atencion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_atencion_paciente"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_atencion_empleado"))
    private Empleado empleado;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private Estado estado;
}
