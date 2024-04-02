package com.project.gestaopagamentos.enums;

public enum Recorrencia {
    SEMANAL("Semanal"),
    MENSAL("Mensal"),
    TRIMESTRAL("Trimestral"),
    SEMESTRAL("Semestral");

    private String recorrencia;

    private Recorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }
}
