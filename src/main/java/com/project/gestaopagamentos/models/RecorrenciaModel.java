package com.project.gestaopagamentos.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.gestaopagamentos.enums.Frequencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "TB_RECORRENCIA")
public class RecorrenciaModel implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @NotNull
    private LocalDateTime dataFinal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Frequencia frequencia;

}
