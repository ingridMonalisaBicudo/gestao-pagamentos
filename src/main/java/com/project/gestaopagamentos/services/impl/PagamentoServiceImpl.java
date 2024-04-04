package com.project.gestaopagamentos.services.impl;

import com.project.gestaopagamentos.dtos.PagamentoRecordDto;
import com.project.gestaopagamentos.dtos.request.PagamentoRequest;
import com.project.gestaopagamentos.enums.Status;
import com.project.gestaopagamentos.enums.TipoChavePix;
import com.project.gestaopagamentos.exceptions.IOException;
import com.project.gestaopagamentos.exceptions.ResourceNotFoundException;
import com.project.gestaopagamentos.helper.NullAwareBeanUtilsBean;
import com.project.gestaopagamentos.mappers.PagamentoMapper;
import com.project.gestaopagamentos.models.PagamentoModel;
import com.project.gestaopagamentos.repositories.PagamentoRepository;
import com.project.gestaopagamentos.services.PagamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.project.gestaopagamentos.utils.PixKeyValidator.isValidEmail;
import static com.project.gestaopagamentos.utils.PixKeyValidator.isValidPhone;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private NullAwareBeanUtilsBean beanUtilsBean;

    @Autowired
    private PagamentoMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(PagamentoServiceImpl.class);
    @Override
    public PagamentoModel create(PagamentoRequest pagamentoRequest) throws IOException { //TODO adicionar logs
        var pagamentoModel = new PagamentoModel();
        var isValidDate = validateDate(pagamentoRequest.getStatus(), pagamentoRequest.getPagamento().toLocalDate());

        if(!isValidDate){ //TODO mudar nome para data Pagamento e o tipo para LocalDate
            throw new IOException("Payment date is not valid");
        }

        var pagamentoList = pagamentoRepository.findByValorAndPagamentoAndDestino(pagamentoRequest.getValor(), pagamentoRequest.getPagamento(), pagamentoRequest.getDestino().getChavePix());
        if(!pagamentoList.isEmpty()){
            log.warn("There is already a payment with the same amount, payment date and pix key");
        }

        //BeanUtils.copyProperties(pagamentoRequest, pagamentoModel);
        pagamentoModel = mapper.toPagamento(pagamentoRequest);
        pagamentoModel.getDestino().setTipoChavePix(getTypePix(pagamentoModel.getDestino().getChavePix()));

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
    public void deleteById(UUID id) throws ResourceNotFoundException { // TODO Colocar uma mensagem avisando se já estiver cancelado
        var pagamentoModel = getById(id);
        pagamentoModel.setStatus(Status.CANCELADO);
        pagamentoRepository.save(pagamentoModel);
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
    private TipoChavePix getTypePix(String pixKey){ //TODO Colocar no mapper

            if(isValidEmail(pixKey)){
                return TipoChavePix.EMAIL;
            } else if(isValidPhone(pixKey)){
                return TipoChavePix.TELEFONE;
            } else {
                return TipoChavePix.ALEATORIA;
            }
    }


}
