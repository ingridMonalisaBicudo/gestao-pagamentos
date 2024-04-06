package com.project.gestaopagamentos.utils;

import com.project.gestaopagamentos.dtos.request.RecorrenciaRequest;
import com.project.gestaopagamentos.enums.Frequencia;
import com.project.gestaopagamentos.models.RecorrenciaModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecorrenciaTestUtil {

    private static final UUID UUID_RECORRENCIA = UUID.randomUUID();

    private static final LocalDateTime DATA_FINAL = LocalDateTime.now();

    public static RecorrenciaRequest createdRecorrenciaRequest(){
        var recorrencia = new RecorrenciaRequest();
        recorrencia.setDataFinal(DATA_FINAL);
        recorrencia.setFrequencia(Frequencia.MENSAL);

        return recorrencia;
    }
}
