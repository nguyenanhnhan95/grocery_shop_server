package com.example.grocery_store_sales_online.service.variationOption.impl;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.mapper.VariationOptionMapper;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.repository.variationOption.VariationOptionRepository;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.variationOption.IVariationOptionService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariationOptionService extends BaseService implements IVariationOptionService {
    private final VariationOptionRepository variationOptionRepository;
    private final VariationOptionMapper variationOptionMapper;

    @Override
    public Optional<VariationOption> findById(Long id) {
        try {
            log.info("VariationOptionService:findById execution started.");
            return variationOptionRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public Optional<VariationOption> findByName(String name) {
        try {
            log.info("VariationOptionService:findByName execution started.");
            return variationOptionRepository.findByName(name);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:findByName to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public VariationOption saveModelDto(VariationOptionDto variationOptionDto) {
        try {
            log.info("VariationOptionService:saveModelDto execution started.");
            if (findByName(variationOptionDto.getName().trim()).isPresent()) {
                BindingResult bindingResult = new BeanPropertyBindingResult(variationOptionDto, "variationOptionDto");
                bindingResult.addError(new FieldError("variationOptionDto", "name", "Tên đã tồn tại"));
                throw new CustomValidationException(bindingResult, EResponseStatus.EXISTING);
            }
            log.info("VariationOptionService:updateModelDto execution started.");
            VariationOption variationOption = variationOptionMapper.convertDtoToVariationOption(variationOptionDto);
            setPersonAction(variationOption);
            setMetaData(variationOption);
            return variationOptionRepository.saveModel(variationOption);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting VariationOptionService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL);
        }
    }

    @Override
    public QueryListResult<VariationOption> getListResult(String queryParameter) {
        try {
            log.info("VariationOptionService:getListResult execution started.");
            return variationOptionRepository.getListResult(readJsonQuery(queryParameter));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public List<VariationOption> findAll() {
        try {
            log.info("VariationOptionService:findAll execution started.");
            return variationOptionRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:findAll to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public void deleteModel(Long id) {
        try {
            log.info("VariationOptionService:deleteModel execution started.");
            VariationOption variationOption = findById(id).get();
            variationOptionRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL);
        }
    }

    @Override
    public VariationOption updateModelDto(Long id, VariationOptionDto variationOptionDto) {
        try {
            log.info("VariationOptionService:updateModelDto execution started.");
            VariationOption variationOption = findById(id).get();
            if (findByName(variationOptionDto.getName().trim()).isPresent() && !variationOptionDto.getName().equals(variationOption.getName())) {
                BindingResult bindingResult = new BeanPropertyBindingResult(variationOptionDto, "variationOptionDto");
                bindingResult.addError(new FieldError("variationOptionDto", "name", "Tên đã tồn tại"));
                throw new CustomValidationException(bindingResult, EResponseStatus.EXISTING);
            }
            setPersonAction(variationOption);
            setMetaData(variationOption);
            variationOptionMapper.updateDtoToVariationOption(variationOptionDto, variationOption);
            return variationOptionRepository.saveModel(variationOption);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL);
        }
    }
}
