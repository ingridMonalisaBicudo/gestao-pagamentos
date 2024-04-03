package com.project.gestaopagamentos.services;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.models.PagamentoModel;
import org.springframework.http.ResponseEntity;

public interface PagamentoServiceInterface {
    PagamentoModel incluir(PagamentoRecordDto pagamentoRecordDto);
}
