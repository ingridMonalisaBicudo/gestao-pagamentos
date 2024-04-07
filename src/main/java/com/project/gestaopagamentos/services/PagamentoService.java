package com.project.gestaopagamentos.services;

import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.dtos.response.PagamentoResponse;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.models.PagamentoModel;

import java.util.List;
import java.util.UUID;

public interface PagamentoService {
    PagamentoResponse create(PagamentoRequest pagamentoRequest) throws IOException;
    List<PagamentoResponse> getAll();
    List<PagamentoResponse> getByStatus(Status status);
    PagamentoModel getById(UUID id) throws ResourceNotFoundException;
    PagamentoResponse updatePagamento(UUID id, PagamentoRequest request) throws ResourceNotFoundException, IOException;
    PagamentoResponse patchUpdatePagamento(UUID id, PagamentoRequest request) throws ResourceNotFoundException, IOException;
    void deleteById(UUID id) throws ResourceNotFoundException;
}
