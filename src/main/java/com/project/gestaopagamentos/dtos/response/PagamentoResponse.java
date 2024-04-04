package com.project.gestaopagamentos.dtos.response;

import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.models.DestinoModel;
import com.project.gestaopagamentos.models.RecorrenciaModel;
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
    private LocalDateTime inclusao;
    private LocalDateTime pagamento;
    private BigDecimal valor;

    private String descricao;
    private RecorrenciaModel recorrencia;
    private DestinoModel destino;
}
