package com.project.gestaopagamentos.enums;

public enum Frequencia {
    SEMANAL("Semanal"),
    MENSAL("Mensal"),
    TRIMESTRAL("Trimestral"),
    SEMESTRAL("Semestral");

    private String frequencia;

    private Frequencia(String frequencia) {
        this.frequencia = frequencia;
    }
}
