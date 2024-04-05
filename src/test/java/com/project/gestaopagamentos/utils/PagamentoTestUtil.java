package com.project.gestaopagamentos.utils;

import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.dtos.response.PagamentoResponse;
import com.project.gestaopagamentos.enums.Frequencia;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.models.DestinoModel;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.models.RecorrenciaModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PagamentoTestUtil {
    private static final UUID UUID_PAGAMENTO = UUID.randomUUID(); //TODO Nao deixar valor random
    private static final LocalDateTime DATA_PAGAMENTO_AGENDADO = LocalDateTime.now().plus(1, ChronoUnit.DAYS);;
    private static final LocalDateTime DATA_PAGAMENTO_EFETUADO = LocalDateTime.now();
    private static final LocalDateTime DATA_FINAL = LocalDateTime.now();
    private static final BigDecimal VALOR_PAGAMENTO = new BigDecimal(200.0);
    private static final String DESCRICAO = "pagamento bancário";
    private static final String CHAVE_PIX_EMAIL = "teste@gmail.com";
    private static final Status STATUS_PAGAMENTO_AGENDADO = Status.AGENDADO;
    private static final Status STATUS_PAGAMENTO_EFETUADO = Status.EFETUADO;
    private static final LocalDateTime DATA_INCLUSAO = LocalDateTime.now();
    private static final UUID UUID_DESTINO = UUID.randomUUID();
    private static final UUID UUID_RECORRENCIA = UUID.randomUUID();


    public static PagamentoRequest createPagamentoRequestAgendado(){
        var request = new PagamentoRequest();
        request.setDataPagamento(DATA_PAGAMENTO_AGENDADO);
        request.setStatus(STATUS_PAGAMENTO_AGENDADO);
        request.setValor(VALOR_PAGAMENTO);
        request.setDestino(createDestino());
        request.setDescricao(DESCRICAO);
        request.setRecorrencia(createdRecorrencia());

        return request;
    }

    public static PagamentoRequest createPagamentoRequestEefetuado(){
        var request = new PagamentoRequest();
        request.setDataPagamento(DATA_PAGAMENTO_EFETUADO);
        request.setStatus(STATUS_PAGAMENTO_EFETUADO);
        request.setValor(VALOR_PAGAMENTO);
        request.setDestino(createDestino());
        request.setDescricao(DESCRICAO);
        request.setRecorrencia(createdRecorrencia());

        return request;
    }

    private static DestinoModel createDestino(){ //TODO criar um DTO tbm e um util de teste
        var destino = new DestinoModel();
        destino.setId(UUID_DESTINO);
        destino.setChavePix(CHAVE_PIX_EMAIL);

        return destino;
    }

    private static RecorrenciaModel createdRecorrencia(){ //TODO criar um DTO tbm e um util de teste
        var recorrencia = new RecorrenciaModel();
        recorrencia.setId(UUID_RECORRENCIA);
        recorrencia.setDataFinal(DATA_FINAL);
        recorrencia.setFrequencia(Frequencia.MENSAL);

        return recorrencia;
    }
    public static PagamentoModel createPagamentoAgendado(){
        var pagamento = new PagamentoModel();
        pagamento.setId(UUID_PAGAMENTO);
        pagamento.setDataPagamento(DATA_PAGAMENTO_AGENDADO);
        pagamento.setValor(VALOR_PAGAMENTO);
        pagamento.setStatus(STATUS_PAGAMENTO_AGENDADO);
        pagamento.setDataInclusao(DATA_PAGAMENTO_AGENDADO);
        pagamento.setDestino(createDestino());
        pagamento.setDescricao(DESCRICAO);
        pagamento.setRecorrencia(createdRecorrencia());

        return pagamento;
    }
    public static PagamentoModel createPagamentoEfetuado(){
        var pagamento = new PagamentoModel();
        pagamento.setId(UUID_PAGAMENTO);
        pagamento.setDataPagamento(DATA_PAGAMENTO_EFETUADO);
        pagamento.setValor(VALOR_PAGAMENTO);
        pagamento.setStatus(STATUS_PAGAMENTO_EFETUADO);
        pagamento.setDataInclusao(DATA_INCLUSAO);
        pagamento.setDestino(createDestino());
        pagamento.setDescricao(DESCRICAO);
        pagamento.setRecorrencia(createdRecorrencia());

        return pagamento;
    }

    public static PagamentoResponse createPagamentoAgendadoResponse(){
        var response = new PagamentoResponse();
        response.setId(UUID_PAGAMENTO);
        response.setDataPagamento(DATA_PAGAMENTO_AGENDADO);
        response.setValor(VALOR_PAGAMENTO);
        response.setStatus(STATUS_PAGAMENTO_AGENDADO);
        response.setInclusao(DATA_PAGAMENTO_AGENDADO);
        response.setDestino(createDestino());
        response.setDescricao(DESCRICAO);
        response.setRecorrencia(createdRecorrencia());

        return response;
    }

    public static PagamentoResponse createPagamentoEfetuadoResponse(){
        var response = new PagamentoResponse();
        response.setId(UUID_PAGAMENTO);
        response.setDataPagamento(DATA_PAGAMENTO_EFETUADO);
        response.setValor(VALOR_PAGAMENTO);
        response.setStatus(STATUS_PAGAMENTO_EFETUADO);
        response.setInclusao(DATA_INCLUSAO);
        response.setDestino(createDestino());
        response.setDescricao(DESCRICAO);
        response.setRecorrencia(createdRecorrencia());

        return response;
    }


    public static List<PagamentoModel> createPagamentoList(){
        List<PagamentoModel> pagamentoList = new ArrayList<>();
        pagamentoList.add(createPagamentoAgendado());
        pagamentoList.add(createPagamentoEfetuado());
        return pagamentoList;
    }
    public static List<PagamentoResponse> createPagamentoResponseList(){
        List<PagamentoResponse> pagamentoList = new ArrayList<>();
        pagamentoList.add(createPagamentoAgendadoResponse());
        pagamentoList.add(createPagamentoEfetuadoResponse());
        return pagamentoList;
    }
}
