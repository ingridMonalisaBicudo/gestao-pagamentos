package com.project.gestaopagamentos.dtos.response;

import com.project.gestaopagamentos.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponse {

    private UUID id;
    private Status status;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataPagamento;
    private BigDecimal valor;

    private String descricao;
    private RecorrenciaResponse recorrencia;
    private DestinoResponse destino;
}
