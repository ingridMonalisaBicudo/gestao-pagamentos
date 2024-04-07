package com.project.gestaopagamentos.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.gestaopagamentos.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequest {
    @NotNull
    private Status status;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dataPagamento;

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String descricao;

    @NotNull
    private RecorrenciaRequest recorrencia;

    @NotNull
    private DestinoRequest destino;
}
