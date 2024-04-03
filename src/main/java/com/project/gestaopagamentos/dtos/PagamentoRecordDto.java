package com.project.gestaopagamentos.dtos;

import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.models.DestinoModel;
import com.project.gestaopagamentos.models.RecorrenciaModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoRecordDto(Status status, LocalDateTime pagamento, BigDecimal valor, String descricao, RecorrenciaModel recorrencia, DestinoModel destino) {
}
