package com.project.gestaopagamentos.controllers;

import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.dtos.response.PagamentoResponse;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.mappers.PagamentoMapper;
import com.project.gestaopagamentos.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@RestController
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @Autowired
    private PagamentoMapper mapper;

    @PostMapping("/pagamentos")
    public ResponseEntity<Object> createPagamento(@RequestBody @Valid PagamentoRequest pagamentoRequest) throws IOException {
           try{
               var pagamento = pagamentoService.create(pagamentoRequest);
               return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
           } catch (IOException e){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           }

    }

    @GetMapping("/pagamentos/{id}")
    public ResponseEntity<Object> getById(@PathVariable (value="id") UUID id) {
        try{
            var pagamentoResponse = mapper.toPagamentoResponse(pagamentoService.getById(id));
            return ResponseEntity.status(HttpStatus.OK).body(pagamentoResponse);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/pagamentos")
    public ResponseEntity<List<PagamentoResponse>> getAllPagamentos(){
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoService.getAll());
    }

    @GetMapping("/pagamentos/status/{status}")
    public ResponseEntity<List<PagamentoResponse>> getByStatus(@PathVariable Status status){
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoService.getByStatus(status));
    }

    @PutMapping("/pagamentos/{id}")
    public ResponseEntity<Object> updatePagamento(@PathVariable (value="id") UUID id, @RequestBody @Valid PagamentoRequest request) {
        try {
            var pagamentoModel = pagamentoService.updatePagamento(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(pagamentoModel);
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/pagamentos/{id}")
    public ResponseEntity<Object> patchUpdatePagamento(@PathVariable (value="id") UUID id, @RequestBody PagamentoRequest request) throws ResourceNotFoundException {
        try {
            var pagamentoModel = pagamentoService.patchUpdatePagamento(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(pagamentoModel);
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/pagamentos/{id}")
    public ResponseEntity<Object> patchUpdatePagamento(@PathVariable (value="id") UUID id) throws ResourceNotFoundException, InvocationTargetException, IllegalAccessException {
        try {
            pagamentoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Pagamento deleted successfuly");
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
