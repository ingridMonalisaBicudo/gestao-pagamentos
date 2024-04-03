package com.project.gestaopagamentos.services.impl;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.repositories.PagamentoRepository;
import com.project.gestaopagamentos.services.PagamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Override
    public PagamentoModel incluir(PagamentoRecordDto pagamentoRecordDto) { //TODO adicionar logs
        var pagamentoModel = new PagamentoModel();
        BeanUtils.copyProperties(pagamentoRecordDto, pagamentoModel);
        return pagamentoRepository.save(pagamentoModel);
    }
    @Override
    public List<PagamentoModel> getAll() {
        return pagamentoRepository.findAll();
    }
    @Override
    public List<PagamentoModel> getByStatus(Status status){ //TODO Tratar caso de status invalido
        return pagamentoRepository.findByStatus(status);
    }

    @Override
    public PagamentoModel getById(UUID id) throws ResourceNotFoundException {

        return pagamentoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pagamento Not Found"));
    }

    @Override
    public PagamentoModel updatePagamento(UUID id , PagamentoRecordDto pagamentoRecordDto) throws ResourceNotFoundException {
        var pagamentoModel = getById(id);
        BeanUtils.copyProperties(pagamentoRecordDto, pagamentoModel);
        pagamentoRepository.save(pagamentoModel);

        return pagamentoModel;
    }
}
