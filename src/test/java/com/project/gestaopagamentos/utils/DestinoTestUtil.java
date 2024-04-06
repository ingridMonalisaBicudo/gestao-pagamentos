package com.project.gestaopagamentos.utils;

import com.project.gestaopagamentos.dtos.request.DestinoRequest;
import com.project.gestaopagamentos.dtos.response.DestinoResponse;
import com.project.gestaopagamentos.enums.TipoChavePix;
import com.project.gestaopagamentos.models.DestinoModel;

import java.util.UUID;

public class DestinoTestUtil {

    private static final UUID UUID_DESTINO = UUID.randomUUID();
    private static final String CHAVE_PIX_EMAIL = "teste@gmail.com";
    private static final TipoChavePix TIPO_PIX_EMAIL = TipoChavePix.EMAIL;
    public static DestinoRequest createDestinoRequest(){
        var destino = new DestinoRequest();
        destino.setChavePix(CHAVE_PIX_EMAIL);

        return destino;
    }

    public static DestinoResponse createDestinoResponse(){
        var destino = new DestinoResponse();
        destino.setId(UUID_DESTINO);
        destino.setChavePix(CHAVE_PIX_EMAIL);
        destino.setTipoChavePix(TIPO_PIX_EMAIL);

        return destino;
    }

    public static DestinoModel createDestinoModel(){
        var destino = new DestinoModel();
        destino.setId(UUID_DESTINO);
        destino.setChavePix(CHAVE_PIX_EMAIL);

        return destino;
    }
}
