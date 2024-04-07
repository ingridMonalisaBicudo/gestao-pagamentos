package com.project.gestaopagamentos.dtos.response;

import com.project.gestaopagamentos.enums.TipoChavePix;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DestinoResponse {

    private UUID id;
    private String chavePix;
    private TipoChavePix tipoChavePix;
}
