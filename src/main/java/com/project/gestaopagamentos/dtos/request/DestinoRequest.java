package com.project.gestaopagamentos.dtos.request;

import com.project.gestaopagamentos.enums.TipoChavePix;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DestinoRequest {
    @NotNull
    @NotBlank
    private String chavePix;
}
