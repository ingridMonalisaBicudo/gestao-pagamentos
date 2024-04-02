package com.project.gestaopagamentos.enums;

public enum TipoChavePix {

    CPF("CPF"),
    EMAIL("Email"),
    TELEFONE("Telefone"),
    ALEATORIA("Aleatoria");

    private String tipoChavePix;

    private TipoChavePix(String tipoChavePix) {
        this.tipoChavePix = tipoChavePix;
    }
}
