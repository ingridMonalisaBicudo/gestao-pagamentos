package com.project.gestaopagamentos.dtos.request;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecorrenciaRequest {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @NotNull
    private LocalDateTime dataFinal;

    @NotNull
    private Frequencia frequencia;
}
