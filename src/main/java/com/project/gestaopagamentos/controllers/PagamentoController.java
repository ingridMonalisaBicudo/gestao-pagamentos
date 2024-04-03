package com.project.gestaopagamentos.controllers;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @PostMapping("/pagamentos")
    public ResponseEntity<PagamentoModel> savePagamento(@RequestBody @Valid PagamentoRecordDto pagamentoRecordDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.incluir(pagamentoRecordDto));
    }
}
