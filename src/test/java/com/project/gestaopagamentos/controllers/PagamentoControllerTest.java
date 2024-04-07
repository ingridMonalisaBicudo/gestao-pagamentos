package com.project.gestaopagamentos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.mappers.PagamentoMapper;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.services.impl.PagamentoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.gestaopagamentos.utils.PagamentoTestUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTest {

    @MockBean
    PagamentoServiceImpl pagamentoService;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PagamentoMapper pagamentoMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_create_should_return_pagamento_status_created() throws Exception {
        var request = createPagamentoRequestAgendado();
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoService.create(any(PagamentoRequest.class))).thenReturn(responseExpected);

        mockMvc.perform(post("/pagamentos")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_create_should_return_io_exception() throws Exception {
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        when(pagamentoService.create(any(PagamentoRequest.class))).thenThrow(new IOException("Payment date is not valid"));

        mockMvc.perform(post("/pagamentos")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Payment date is not valid"));
    }
    @Test
    public void test_get_all_should_return_pagamento_list_status_ok() throws Exception {
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoService.getAll()).thenReturn(List.of(responseExpected));

        mockMvc.perform(get("/pagamentos"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_by_status_should_return_pagamento_list_status_ok() throws Exception {
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoService.getByStatus(any(Status.class))).thenReturn(List.of(responseExpected));

        mockMvc.perform(get("/pagamentos/{status}", Status.AGENDADO))
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_by_id_should_return_pagamento_status_ok() throws Exception {
        UUID id = UUID.randomUUID();
        var pagamentoModel = createPagamentoAgendado();
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoService.getById(any(UUID.class))).thenReturn(pagamentoModel);
        when(pagamentoMapper.toPagamentoResponse(any(PagamentoModel.class))).thenReturn(responseExpected);

        mockMvc.perform(get("/pagamentos/id/{id}", id))
                .andExpect(status().isOk());
    }
    @Test
    public void test_get_by_id_should_return_resource_not_found_exception() throws Exception {
        UUID id = UUID.randomUUID();

        when(pagamentoService.getById(id)).thenThrow(new ResourceNotFoundException("Payment Not Found"));

        mockMvc.perform(get("/pagamentos/id/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Payment Not Found"));
    }

    @Test
    public void test_update_should_return_pagamento_status_ok() throws Exception {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoService.updatePagamento(any(UUID.class),any(PagamentoRequest.class))).thenReturn(responseExpected);

        mockMvc.perform(put("/pagamentos/{id}", id)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_update_should_return_io_exception() throws Exception {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        when(pagamentoService.updatePagamento(any(UUID.class),any(PagamentoRequest.class))).thenThrow(new IOException("Payment date is not valid"));

        mockMvc.perform(put("/pagamentos/{id}", id)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Payment date is not valid"));
    }

    @Test
    public void test_update_should_return_resource_not_found_exception() throws Exception {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        when(pagamentoService.updatePagamento(any(UUID.class),any(PagamentoRequest.class))).thenThrow(new ResourceNotFoundException("Payment Not Found"));

        mockMvc.perform(put("/pagamentos/{id}", id)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Payment Not Found"));
    }
    @Test
    public void test_patch_update_should_return_pagamento_status_ok() throws Exception {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoService.patchUpdatePagamento(any(UUID.class),any(PagamentoRequest.class))).thenReturn(responseExpected);

        mockMvc.perform(patch("/pagamentos/{id}", id)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_patch_update_should_return_io_exception() throws Exception {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        when(pagamentoService.patchUpdatePagamento(any(UUID.class),any(PagamentoRequest.class))).thenThrow(new IOException("Payment date is not valid"));

        mockMvc.perform(patch("/pagamentos/{id}", id)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Payment date is not valid"));
    }

    @Test
    public void test_patch_update_should_return_resource_not_found_exception() throws Exception {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        when(pagamentoService.patchUpdatePagamento(any(UUID.class),any(PagamentoRequest.class))).thenThrow(new ResourceNotFoundException("Payment Not Found"));

        mockMvc.perform(patch("/pagamentos/{id}", id)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Payment Not Found"));
    }
    @Test
    public void test_delete_should_return_ok() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(pagamentoService).deleteById(any(UUID.class));

        mockMvc.perform(delete("/pagamentos/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void test_delete_should_return_resource_not_found_exception() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new ResourceNotFoundException("Payment Not Found")).when(pagamentoService).deleteById(id);

        mockMvc.perform(delete("/pagamentos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Payment Not Found"));
    }
}
