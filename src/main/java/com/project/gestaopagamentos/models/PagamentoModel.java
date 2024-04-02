package com.project.gestaopagamentos.models;

import com.project.gestaopagamentos.enums.Recorrencia;
import com.project.gestaopagamentos.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PAGAMENTOS")
public class PagamentoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
    private LocalDateTime inclusao;
    private LocalDateTime pagamento;
    private BigDecimal valor;

    private String descricao;
    @Enumerated(EnumType.STRING)
    private Recorrencia recorrencia;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "destino_id")
    private DestinoModel destino; //TODO criar a entity pra isso

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getInclusao() {
        return inclusao;
    }

    public void setInclusao(LocalDateTime inclusao) {
        this.inclusao = inclusao;
    }

    public LocalDateTime getPagamento() {
        return pagamento;
    }

    public void setPagamento(LocalDateTime pagamento) {
        this.pagamento = pagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Recorrencia getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(Recorrencia recorrencia) {
        this.recorrencia = recorrencia;
    }

    public DestinoModel getDestino() {
        return destino;
    }

    public void setDestino(DestinoModel destino) {
        this.destino = destino;
    }
}
