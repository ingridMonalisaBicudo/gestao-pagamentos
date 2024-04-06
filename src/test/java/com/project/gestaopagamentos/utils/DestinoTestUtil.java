package com.project.gestaopagamentos.utils;

import com.project.gestaopagamentos.dtos.request.DestinoRequest;
import com.project.gestaopagamentos.models.DestinoModel;

import java.util.UUID;

public class DestinoTestUtil {

    private static final UUID UUID_DESTINO = UUID.randomUUID();
    private static final String CHAVE_PIX_EMAIL = "teste@gmail.com";
    public static DestinoRequest createDestinoRequest(){
        var destino = new DestinoRequest();
        destino.setChavePix(CHAVE_PIX_EMAIL);

        return destino;
    }
}
