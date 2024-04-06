package com.project.gestaopagamentos.services;

import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.dtos.response.PagamentoResponse;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.mappers.PagamentoMapper;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.repositories.PagamentoRepository;
import com.project.gestaopagamentos.services.impl.PagamentoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.gestaopagamentos.utils.PagamentoTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PagamentoServiceTest {

    @Mock
    PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoMapper mapper;

    @InjectMocks
    PagamentoServiceImpl pagamentoServiceImpl;

    @Test
    public void when_save_payment_should_return_correct_response() throws IOException {
        var request = createPagamentoRequestAgendado();
        var pagamento = createPagamentoAgendado();
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoRepository.save(any(PagamentoModel.class))).thenReturn(pagamento);
        when(pagamentoRepository.findByValorAndPagamentoAndDestino(any(),any() ,anyString())).thenReturn(Collections.emptyList());
        when(mapper.toPagamento(any(PagamentoRequest.class))).thenReturn(pagamento);
        when(mapper.toPagamentoResponse(any(PagamentoModel.class))).thenReturn(responseExpected);

        PagamentoResponse response = pagamentoServiceImpl.create(request);

        assertEquals(responseExpected.getDataPagamento(), response.getDataPagamento());
        assertEquals(responseExpected.getValor(), response.getValor());
        assertEquals(responseExpected.getStatus(), response.getStatus());
        assertEquals(responseExpected.getDestino().getTipoChavePix(), response.getDestino().getTipoChavePix());
        assertEquals(responseExpected.getDestino().getChavePix(), response.getDestino().getChavePix());
        assertEquals(responseExpected.getRecorrencia().getFrequencia(), response.getRecorrencia().getFrequencia());
        assertEquals(responseExpected.getRecorrencia().getDataFinal(), response.getRecorrencia().getDataFinal());
    }

    @Test
    public void when_try_create_payment_by_id_with_invalid_date_status_agendado_should_return_resource_io_exception() {
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now());

        assertThrows(IOException.class, () -> pagamentoServiceImpl.create(request));
    }

    @Test
    public void when_try_create_payment_by_id_with_invalid_date_status_efetuado_should_return_resource_io_exception() {
        var request = createPagamentoRequestEefetuado();
        request.setDataPagamento( LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        assertThrows(IOException.class, () -> pagamentoServiceImpl.create(request));
    }

    @Test
    public void when_try_create_payment_by_id_with_invalid_date_in_the_past_should_return_resource_io_exception() {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestEefetuado();
        request.setDataPagamento( LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        assertThrows(IOException.class, () -> pagamentoServiceImpl.create(request));
    }
    @Test
    public void when_get_all_payments_should_return_correct_response_list() {
        var pagamentoList = createPagamentoList();
        var responseListExpected = createPagamentoResponseList();

        when(pagamentoRepository.findAll()).thenReturn(pagamentoList);
        when(mapper.toPagamentoResponseList(anyList())).thenReturn(responseListExpected);

        List<PagamentoResponse> response = pagamentoServiceImpl.getAll();

        assertEquals(responseListExpected.get(0).getDataPagamento(), response.get(0).getDataPagamento());
        assertEquals(responseListExpected.get(0).getValor(), response.get(0).getValor());
        assertEquals(responseListExpected.get(0).getStatus(), response.get(0).getStatus());
        assertEquals(responseListExpected.get(0).getDestino().getTipoChavePix(), response.get(0).getDestino().getTipoChavePix());
        assertEquals(responseListExpected.get(0).getDestino().getChavePix(), response.get(0).getDestino().getChavePix());
        assertEquals(responseListExpected.get(0).getRecorrencia().getFrequencia(), response.get(0).getRecorrencia().getFrequencia());
        assertEquals(responseListExpected.get(0).getRecorrencia().getDataFinal(), response.get(0).getRecorrencia().getDataFinal());
    }

    @Test
    public void when_get_payment_by_status_should_return_correct_response_list() {
        var status = Status.AGENDADO;
        var pagamentoList = List.of(createPagamentoAgendado());
        var responseListExpected = List.of(createPagamentoAgendadoResponse());

        when(pagamentoRepository.findByStatus(any(Status.class))).thenReturn(pagamentoList);
        when(mapper.toPagamentoResponseList(anyList())).thenReturn(responseListExpected);

        List<PagamentoResponse> response = pagamentoServiceImpl.getByStatus(status);

        assertEquals(responseListExpected.get(0).getDataPagamento(), response.get(0).getDataPagamento());
        assertEquals(responseListExpected.get(0).getValor(), response.get(0).getValor());
        assertEquals(responseListExpected.get(0).getStatus(), response.get(0).getStatus());
        assertEquals(responseListExpected.get(0).getDestino().getTipoChavePix(), response.get(0).getDestino().getTipoChavePix());
        assertEquals(responseListExpected.get(0).getDestino().getChavePix(), response.get(0).getDestino().getChavePix());
        assertEquals(responseListExpected.get(0).getRecorrencia().getFrequencia(), response.get(0).getRecorrencia().getFrequencia());
        assertEquals(responseListExpected.get(0).getRecorrencia().getDataFinal(), response.get(0).getRecorrencia().getDataFinal());
    }

    @Test
    public void when_get_payment_by_id_should_return_correct_response() throws ResourceNotFoundException {
        var pagamento = createPagamentoAgendado();
        UUID id = pagamento.getId();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamento));

        PagamentoModel response = pagamentoServiceImpl.getById(id);

        assertEquals(pagamento.getDataPagamento(), response.getDataPagamento());
        assertEquals(pagamento.getValor(), response.getValor());
        assertEquals(pagamento.getStatus(), response.getStatus());
        assertEquals(pagamento.getDestino().getTipoChavePix(), response.getDestino().getTipoChavePix());
        assertEquals(pagamento.getDestino().getChavePix(), response.getDestino().getChavePix());
        assertEquals(pagamento.getRecorrencia().getFrequencia(), response.getRecorrencia().getFrequencia());
        assertEquals(pagamento.getRecorrencia().getDataFinal(), response.getRecorrencia().getDataFinal());
    }

    @Test
    public void when_get_payment_by_id_should_return_resource_not_found_exception() {

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagamentoServiceImpl.getById(UUID.randomUUID()));
    }

    @Test
    public void when_update_payment_to_agendado_should_return_correct_response() throws ResourceNotFoundException, IOException {
        var request = createPagamentoRequestAgendado();
        UUID id = UUID.randomUUID();
        var pagamentoAgendado = createPagamentoAgendado();
        var pagamentoEfetuado = createPagamentoEfetuado();
        var responseExpected = createPagamentoEfetuadoResponse();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamentoAgendado));
        when(pagamentoRepository.save(any(PagamentoModel.class))).thenReturn(pagamentoEfetuado);
        when(mapper.toPagamentoResponse(any(PagamentoModel.class))).thenReturn(responseExpected);

        PagamentoResponse response = pagamentoServiceImpl.updatePagamento(id, request);


        assertEquals(Status.EFETUADO, response.getStatus());
        assertEquals(responseExpected.getDataPagamento(), response.getDataPagamento());
        assertEquals(responseExpected.getValor(), response.getValor());
        assertEquals(responseExpected.getDestino().getTipoChavePix(), response.getDestino().getTipoChavePix());
        assertEquals(responseExpected.getDestino().getChavePix(), response.getDestino().getChavePix());
        assertEquals(responseExpected.getRecorrencia().getFrequencia(), response.getRecorrencia().getFrequencia());
        assertEquals(responseExpected.getRecorrencia().getDataFinal(), response.getRecorrencia().getDataFinal());
    }

    @Test
    public void when_try_update_payment_by_id_with_invalid_date_status_agendado_should_return_resource_io_exception() {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now());

        assertThrows(IOException.class, () -> pagamentoServiceImpl.updatePagamento(id, request));
    }

    @Test
    public void when_try_update_payment_by_id_with_invalid_date_status_efetuado_should_return_resource_io_exception() {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestEefetuado();
        request.setDataPagamento( LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        assertThrows(IOException.class, () -> pagamentoServiceImpl.updatePagamento(id, request));
    }
    @Test
    public void when_try_update_payment_by_id_with_invalid_date_in_the_past_should_return_resource_io_exception() {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestEefetuado();
        request.setDataPagamento( LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        assertThrows(IOException.class, () -> pagamentoServiceImpl.updatePagamento(id, request));
    }
    @Test
    public void when_try_update_payment_by_id_should_return_resource_not_found_exception() {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagamentoServiceImpl.updatePagamento(id, request));
    }

    @Test
    public void when_update_patch_payment_should_return_correct_response() throws ResourceNotFoundException, InvocationTargetException, IllegalAccessException, IOException {
        var request = createPagamentoRequestAgendado();
        UUID id = UUID.randomUUID();
        var pagamento = createPagamentoAgendado();
        var responseExpected = createPagamentoAgendadoResponse();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(PagamentoModel.class))).thenReturn(pagamento);
        when(mapper.toPagamentoResponse(any(PagamentoModel.class))).thenReturn(responseExpected);

        PagamentoResponse response = pagamentoServiceImpl.patchUpdatePagamento(id, request);

        assertEquals(responseExpected.getDataPagamento(), response.getDataPagamento());
        assertEquals(responseExpected.getValor(), response.getValor());
        assertEquals(responseExpected.getStatus(), response.getStatus());
        assertEquals(responseExpected.getDestino().getChavePix(), response.getDestino().getChavePix());
        assertEquals(responseExpected.getDestino().getTipoChavePix(), response.getDestino().getTipoChavePix());
        assertEquals(responseExpected.getRecorrencia().getFrequencia(), response.getRecorrencia().getFrequencia());
        assertEquals(responseExpected.getRecorrencia().getDataFinal(), response.getRecorrencia().getDataFinal());
    }

    @Test
    public void when_try_update_patch_payment_by_id_with_invalid_date_status_agendado_should_return_resource_io_exception() {
        UUID id = UUID.randomUUID();
        var pagamento = createPagamentoAgendado();
        var request = createPagamentoRequestAgendado();
        request.setDataPagamento(LocalDateTime.now());

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamento));

        assertThrows(IOException.class, () -> pagamentoServiceImpl.patchUpdatePagamento(id, request));
    }

    @Test
    public void when_try_update_patch_payment_by_id_with_invalid_date_status_efetuado_should_return_resource_io_exception() {
        UUID id = UUID.randomUUID();
        var pagamento = createPagamentoEfetuado();
        var request = createPagamentoRequestEefetuado();
        request.setDataPagamento( LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamento));

        assertThrows(IOException.class, () -> pagamentoServiceImpl.patchUpdatePagamento(id, request));
    }
    @Test
    public void when_try_update_patch_payment_by_id_with_invalid_date_in_the_past_should_return_resource_io_exception() {
        UUID id = UUID.randomUUID();
        var pagamento = createPagamentoEfetuado();
        var request = createPagamentoRequestEefetuado();
        request.setDataPagamento( LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamento));

        assertThrows(IOException.class, () -> pagamentoServiceImpl.patchUpdatePagamento(id, request));
    }
    @Test
    public void when_try_update_patch_payment_by_id_should_return_resource_not_found_exception() {
        UUID id = UUID.randomUUID();
        var request = createPagamentoRequestAgendado();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagamentoServiceImpl.patchUpdatePagamento(id, request));
    }
    @Test
    public void when_delete_payment_should_return_correct_response() throws ResourceNotFoundException {
        UUID id = UUID.randomUUID();
        var pagamento = createPagamentoAgendado();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(PagamentoModel.class))).thenReturn(pagamento);

        pagamentoServiceImpl.deleteById(id);

        verify(pagamentoRepository).findById(any(UUID.class));
        verify(pagamentoRepository).save(any(PagamentoModel.class));
    }
    @Test
    public void when_try_delete_payment_by_id_should_return_resource_not_found_exception() {
        UUID id = UUID.randomUUID();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagamentoServiceImpl.deleteById(id));
    }

}
