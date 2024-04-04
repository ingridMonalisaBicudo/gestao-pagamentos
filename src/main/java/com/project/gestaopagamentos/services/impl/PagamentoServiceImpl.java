package com.project.gestaopagamentos.services.impl;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.helper.NullAwareBeanUtilsBean;
import com.project.gestaopagamentos.models.DestinoModel;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.repositories.PagamentoRepository;
import com.project.gestaopagamentos.services.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private NullAwareBeanUtilsBean beanUtilsBean;

    private static final Logger log = LoggerFactory.getLogger(PagamentoServiceImpl.class);
    @Override
    public PagamentoModel create(PagamentoRecordDto pagamentoRecordDto) throws IOException { //TODO adicionar logs
        var pagamentoModel = new PagamentoModel();
        var isValidDate = validateDate(pagamentoRecordDto.status(), pagamentoRecordDto.pagamento().toLocalDate());

        if(!isValidDate){ //TODO mudar nome para data Pagamento e o tipo para LocalDate
            throw new IOException("Payment date is not valid");
        }

        var pagamentoList = pagamentoRepository.findByValorAndPagamentoAndDestino(pagamentoRecordDto.valor(), pagamentoRecordDto.pagamento(), pagamentoRecordDto.destino().getChavePix());
        if(!pagamentoList.isEmpty()){
            log.warn("There is already a payment with the same amount, payment date and pix key");
        }

        BeanUtils.copyProperties(pagamentoRecordDto, pagamentoModel);
        return pagamentoRepository.save(pagamentoModel);
    }
    @Override
    public List<PagamentoModel> getAll() {
        return pagamentoRepository.findAll();
    }
    @Override
    public List<PagamentoModel> getByStatus(Status status){ //TODO Tratar caso de status invalido
        return pagamentoRepository.findByStatus(status);
    }

    @Override
    public PagamentoModel getById(UUID id) throws ResourceNotFoundException {

        return pagamentoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Payment Not Found"));
    }

    @Override
    public PagamentoModel updatePagamento(UUID id , PagamentoRecordDto pagamentoRecordDto) throws ResourceNotFoundException {
        var pagamentoModel = getById(id);
        BeanUtils.copyProperties(pagamentoRecordDto, pagamentoModel);
        pagamentoRepository.save(pagamentoModel);

        return pagamentoModel;
    }

    @Override
    public PagamentoModel patchUpdatePagamento(UUID id, PagamentoRecordDto pagamentoRecordDto) throws ResourceNotFoundException, InvocationTargetException, IllegalAccessException { //TODO tratar as duas 2 outras exceções
        var pagamentoModel = getById(id);
        beanUtilsBean.copyProperties(pagamentoRecordDto, pagamentoModel);
        pagamentoRepository.save(pagamentoModel);

        return pagamentoModel;
    }

    @Override
    public void deleteById(UUID id) throws ResourceNotFoundException { // TODO delete vai apenas mudar o registro para CANCELADO
        var pagamentoModel = getById(id);
        pagamentoRepository.deleteById(id);
    }

    private static boolean validateDate (Status status, LocalDate dateToValidate){ //TODO colocar isso no PUT e no PATCH
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

}
