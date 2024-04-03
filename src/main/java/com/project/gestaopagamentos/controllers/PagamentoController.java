package com.project.gestaopagamentos.controllers;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @PostMapping("/pagamentos")
    public ResponseEntity<PagamentoModel> savePagamento(@RequestBody @Valid PagamentoRecordDto pagamentoRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.incluir(pagamentoRecordDto));
    }

    @GetMapping("/pagamentos")
    public ResponseEntity<List<PagamentoModel>> getAllPagamentos(){
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoService.getAll());
    }
}
