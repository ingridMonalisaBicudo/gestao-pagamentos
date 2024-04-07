package com.project.gestaopagamentos.models;

import com.project.gestaopagamentos.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "TB_PAGAMENTOS")
public class PagamentoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @NotNull
    private LocalDateTime dataPagamento;

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String descricao;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recorrencia_id",  nullable = true)
    private RecorrenciaModel recorrencia;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "destino_id")
    @NotNull
    private DestinoModel destino;

    @PrePersist
    public void prePersist() {
        this.dataInclusao = LocalDateTime.now();
    }
}
