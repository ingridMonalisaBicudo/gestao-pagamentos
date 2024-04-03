package com.project.gestaopagamentos.repositories;

import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.models.PagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, UUID> {
    List<PagamentoModel> findByStatus(Status status);
}
