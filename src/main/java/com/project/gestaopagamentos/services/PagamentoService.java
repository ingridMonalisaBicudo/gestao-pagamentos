package com.project.gestaopagamentos.services;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.models.PagamentoModel;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public interface PagamentoService {
    PagamentoModel create(PagamentoRequest pagamentoRequest) throws IOException;
    List<PagamentoModel> getAll();
    List<PagamentoModel> getByStatus(Status status);
    PagamentoModel getById(UUID id) throws ResourceNotFoundException;
    PagamentoModel updatePagamento(UUID id, PagamentoRecordDto pagamentoRecordDto) throws ResourceNotFoundException;
    PagamentoModel patchUpdatePagamento(UUID id, PagamentoRecordDto pagamentoRecordDto) throws ResourceNotFoundException, InvocationTargetException, IllegalAccessException;
    void deleteById(UUID id) throws ResourceNotFoundException;
}
