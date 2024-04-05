package com.project.gestaopagamentos.models;

import com.project.gestaopagamentos.enums.TipoChavePix;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "TB_DESTINO")
public class DestinoModel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotBlank
    private String chavePix;

    @Enumerated(EnumType.STRING)
    private TipoChavePix tipoChavePix;

}
