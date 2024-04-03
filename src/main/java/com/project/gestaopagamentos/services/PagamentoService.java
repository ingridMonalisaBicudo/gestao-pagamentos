package com.project.gestaopagamentos.services;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.repositories.PagamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoService implements PagamentoServiceInterface{

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Override
    public PagamentoModel incluir(PagamentoRecordDto pagamentoRecordDto) {
        var pagamentoModel = new PagamentoModel();
        BeanUtils.copyProperties(pagamentoRecordDto, pagamentoModel);
        return pagamentoRepository.save(pagamentoModel);
    }
    @Override
    public List<PagamentoModel> getAll() {
        return pagamentoRepository.findAll();
    }
}
