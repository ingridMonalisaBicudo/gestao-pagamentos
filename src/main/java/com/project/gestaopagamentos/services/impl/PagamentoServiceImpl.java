package com.project.gestaopagamentos.services.impl;

import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.dtos.response.PagamentoResponse;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.enums.TipoChavePix;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.mappers.PagamentoMapper;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.repositories.PagamentoRepository;
import com.project.gestaopagamentos.services.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.project.gestaopagamentos.utils.PixKeyValidator.isValidEmail;
import static com.project.gestaopagamentos.utils.PixKeyValidator.isValidPhone;

@Service
@Slf4j
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private PagamentoMapper mapper;

    @Override
    @Transactional
    public PagamentoResponse create(PagamentoRequest pagamentoRequest) throws IOException {
        var pagamentoModel = new PagamentoModel();
        var isValidDate = validateDate(pagamentoRequest.getStatus(), pagamentoRequest.getDataPagamento().toLocalDate());

        if(!isValidDate){
            throw new IOException("Payment date is not valid");
        }

        var pagamentoList = pagamentoRepository.findByValorAndPagamentoAndDestino(pagamentoRequest.getValor(), pagamentoRequest.getDataPagamento(), pagamentoRequest.getDestino().getChavePix());
        if(!pagamentoList.isEmpty()){
            log.warn("There is already a payment with the same amount, payment date and pix key");
        }

        pagamentoModel = mapper.toPagamento(pagamentoRequest);
        pagamentoModel.getDestino().setTipoChavePix(getTypePix(pagamentoModel.getDestino().getChavePix()));

        var pagamentoCreated = pagamentoRepository.save(pagamentoModel);
        log.info("Payment created successfully: [ID: {}, Status: {}, Value: {}]", pagamentoCreated.getId(), pagamentoCreated.getStatus(), pagamentoCreated.getValor());

        return mapper.toPagamentoResponse(pagamentoCreated);
    }
    @Override
    public List<PagamentoResponse> getAll() {
        var pagamentoList = pagamentoRepository.findAll();
        return mapper.toPagamentoResponseList(pagamentoList);
    }
    @Override
    public List<PagamentoResponse> getByStatus(Status status){
        var pagamentoList = pagamentoRepository.findByStatus(status);
        return mapper.toPagamentoResponseList(pagamentoList);
    }

    @Override
    public PagamentoModel getById(UUID id) throws ResourceNotFoundException {
        var pagamento = pagamentoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Payment Not Found"));
        return pagamento;
    }

    @Override
    @Transactional
    public PagamentoResponse updatePagamento(UUID id , PagamentoRequest request) throws ResourceNotFoundException, IOException {
        var isValidDate = validateDate(request.getStatus(), request.getDataPagamento().toLocalDate());
        if(!isValidDate){
            throw new IOException("Payment date is not valid");
        }

        var pagamentoModel = getById(id);
        pagamentoModel = mapper.merge(pagamentoModel, request);
        pagamentoModel.getDestino().setTipoChavePix(getTypePix(pagamentoModel.getDestino().getChavePix()));

        var pagamentoUpdated = pagamentoRepository.save(pagamentoModel);
        log.info("Payment updated successfully: [ID: {}, Status: {}, Value: {}]", pagamentoUpdated.getId(), pagamentoUpdated.getStatus(), pagamentoUpdated.getValor());

        return mapper.toPagamentoResponse(pagamentoUpdated);
    }

    @Override
    @Transactional
    public PagamentoResponse patchUpdatePagamento(UUID id, PagamentoRequest request) throws ResourceNotFoundException, IOException {
        var pagamento = getById(id);

        if(request.getDataPagamento() != null && !validateDate(pagamento.getStatus(), request.getDataPagamento().toLocalDate())){
            throw new IOException("Payment date is not valid");
        }

        var pagamentoMerged = mapper.merge(pagamento, request);

        if(pagamentoMerged.getDestino().getChavePix() != null){
            pagamentoMerged.getDestino().setTipoChavePix(getTypePix(pagamento.getDestino().getChavePix()));
        }

        var pagamentoUpdated = pagamentoRepository.save(pagamentoMerged);
        log.info("Payment updated successfully: [ID: {}, Status: {}, Value: {}]", pagamentoUpdated.getId(), pagamentoUpdated.getStatus(), pagamentoUpdated.getValor());

        return mapper.toPagamentoResponse(pagamentoUpdated);
    }

    @Override
    public void deleteById(UUID id) throws ResourceNotFoundException {
        var pagamentoModel = getById(id);
        pagamentoModel.setStatus(Status.CANCELADO);
        pagamentoRepository.save(pagamentoModel);
    }

    private static boolean validateDate (Status status, LocalDate dateToValidate){
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        if(dateToValidate.isBefore(currentDate)){
            return false;
        } else if(status == Status.EFETUADO && !dateToValidate.isEqual(currentDate)){
            return false;
        } else if(status == Status.AGENDADO && !dateToValidate.isAfter(currentDate)){
            return false;
        }

        return true;
    }
    private TipoChavePix getTypePix(String pixKey){

            if(isValidEmail(pixKey)){
                return TipoChavePix.EMAIL;
            } else if(isValidPhone(pixKey)){
                return TipoChavePix.TELEFONE;
            } else {
                return TipoChavePix.ALEATORIA;
            }
    }


}
