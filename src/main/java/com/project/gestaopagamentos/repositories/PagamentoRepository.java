package com.project.gestaopagamentos.repositories;

import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.models.PagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, UUID> {
    List<PagamentoModel> findByStatus(Status status);
    @Query("SELECT p FROM PagamentoModel p JOIN p.destino d WHERE p.valor = :valor AND p.dataPagamento = :dataPagamento AND d.chavePix = :chavePix")
    List<PagamentoModel> findByValorAndPagamentoAndDestino(@Param("valor") BigDecimal valor, @Param("dataPagamento") LocalDateTime pagamento, @Param("chavePix") String chavePix);
}