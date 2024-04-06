package com.project.gestaopagamentos.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.gestaopagamentos.enums.Frequencia;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecorrenciaResponse {

    private UUID id;
    private LocalDateTime dataFinal;
    private Frequencia frequencia;

}
