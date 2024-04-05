package com.project.gestaopagamentos.dtos.request;

import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.models.DestinoModel;
import com.project.gestaopagamentos.models.RecorrenciaModel;
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
    private LocalDateTime dataPagamento;

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String descricao;

    @NotNull
    private RecorrenciaModel recorrencia;

    @NotNull
    private DestinoModel destino;
}
