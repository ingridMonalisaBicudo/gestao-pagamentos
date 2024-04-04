package com.project.gestaopagamentos.models;

import com.project.gestaopagamentos.enums.Frequencia;
import jakarta.persistence.*;
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
    private LocalDateTime dataFinal;
    @Enumerated(EnumType.STRING)
    private Frequencia frequencia;

}
