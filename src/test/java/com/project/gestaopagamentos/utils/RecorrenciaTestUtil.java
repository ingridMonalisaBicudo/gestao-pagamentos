package com.project.gestaopagamentos.utils;

import com.project.gestaopagamentos.dtos.request.RecorrenciaRequest;
import com.project.gestaopagamentos.dtos.response.RecorrenciaResponse;
import com.project.gestaopagamentos.enums.Frequencia;
import com.project.gestaopagamentos.models.RecorrenciaModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecorrenciaTestUtil {

    private static final UUID UUID_RECORRENCIA = UUID.randomUUID();
    private static final LocalDateTime DATA_FINAL = LocalDateTime.now();
    private static final Frequencia FREQUENCIA = Frequencia.MENSAL;

    public static RecorrenciaRequest creatRecorrenciaRequest(){
        var recorrencia = new RecorrenciaRequest();
        recorrencia.setDataFinal(DATA_FINAL);
        recorrencia.setFrequencia(Frequencia.MENSAL);

        return recorrencia;
    }

    public static RecorrenciaResponse creatRecorrenciaResponse(){
        var recorrencia = new RecorrenciaResponse();
        recorrencia.setId(UUID_RECORRENCIA);
        recorrencia.setDataFinal(DATA_FINAL);
        recorrencia.setFrequencia(FREQUENCIA);

        return recorrencia;
    }

    public static RecorrenciaModel creatRecorrenciaModel(){
        var recorrencia = new RecorrenciaModel();
        recorrencia.setId(UUID_RECORRENCIA);
        recorrencia.setDataFinal(DATA_FINAL);
        recorrencia.setFrequencia(Frequencia.MENSAL);

        return recorrencia;
    }
}
