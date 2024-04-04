package com.project.gestaopagamentos.mappers;

import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.models.PagamentoModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagamentoMapper {

    private final ModelMapper mapper;
    public PagamentoModel toPagamento(PagamentoRequest request) {
        return mapper.map(request, PagamentoModel.class);
    }
}
