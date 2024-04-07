package com.project.gestaopagamentos.mappers;

import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.dtos.response.PagamentoResponse;
import com.project.gestaopagamentos.models.PagamentoModel;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PagamentoMapper {

    private final ModelMapper mapper;

    public PagamentoMapper(ModelMapper modelMapper) {
        this.mapper = modelMapper;

        this.mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public PagamentoModel toPagamento(PagamentoRequest request) {
        return mapper.map(request, PagamentoModel.class);
    }
    public PagamentoResponse toPagamentoResponse(PagamentoModel pagamento) {
        return mapper.map(pagamento, PagamentoResponse.class);
    }
    public List<PagamentoResponse> toPagamentoResponseList(List<PagamentoModel> pagamentos) {
        return pagamentos.stream()
                .map(this::toPagamentoResponse)
                .collect(Collectors.toList());
    }

    public PagamentoModel merge(PagamentoModel model, PagamentoRequest request) {
        mapper.map(request, model);
        return model;
    }
}
