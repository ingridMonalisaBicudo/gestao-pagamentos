package com.project.gestaopagamentos.enums;

public enum Status {
    EFETUADO("Efetuado"),
    AGENDADO("Agendado"),
    CANCELADO("Cancelado");

    private String status;

    private Status(String status) {
        this.status = status;
    }
}
