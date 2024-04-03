package com.project.gestaopagamentos.services;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.models.PagamentoModel;

import java.util.List;

public interface PagamentoServiceInterface {
    PagamentoModel incluir(PagamentoRecordDto pagamentoRecordDto);
    List<PagamentoModel> getAll();
}
