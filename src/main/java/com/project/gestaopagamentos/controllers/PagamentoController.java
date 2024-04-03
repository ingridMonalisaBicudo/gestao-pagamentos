package com.project.gestaopagamentos.controllers;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/pagamentos/{status}")
    public ResponseEntity<List<PagamentoModel>> getByStatus(@PathVariable Status status){
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoService.getByStatus(status));
    }

    @PutMapping("/pagamentos/{id}")
    public ResponseEntity<Object> updatePagamento(@PathVariable (value="id") UUID id, @RequestBody @Valid PagamentoRecordDto pagamentoRecordDto) throws ResourceNotFoundException {
        try {
            var pagamentoModel = pagamentoService.updatePagamento(id, pagamentoRecordDto);
            return ResponseEntity.status(HttpStatus.OK).body(pagamentoModel);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/pagamentos/{id}")
    public ResponseEntity<Object> patchUpdatePagamento(@PathVariable (value="id") UUID id, @RequestBody @Valid PagamentoRecordDto pagamentoRecordDto) throws ResourceNotFoundException {
        try {
            var pagamentoModel = pagamentoService.patchUpdatePagamento(id, pagamentoRecordDto);
            return ResponseEntity.status(HttpStatus.OK).body(pagamentoModel);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/pagamentos/{id}")
    public ResponseEntity<Object> patchUpdatePagamento(@PathVariable (value="id") UUID id) throws ResourceNotFoundException {
        try {
            pagamentoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Pagamento deleted successfuly");
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
