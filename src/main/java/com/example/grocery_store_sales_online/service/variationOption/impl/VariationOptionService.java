package com.example.grocery_store_sales_online.service.variationOption.impl;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.mapper.VariationOptionMapper;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.repository.variationOption.VariationOptionRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.variation.IVariationService;
import com.example.grocery_store_sales_online.service.variationOption.IVariationOptionService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_ALREADY_EXIST;
import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_CANNOT_EMPTY;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariationOptionService extends BaseService implements IVariationOptionService {
    private final VariationOptionRepository variationOptionRepository;
    private final VariationOptionMapper variationOptionMapper;

    private final IVariationService variationService;
    @Override
    public Optional<VariationOption> findById(Long id) {
        try {
            log.info("VariationOptionService:findById execution started.");
            return variationOptionRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<VariationOption> findByName(String name) {
        try {
            log.info("VariationOptionService:findByName execution started.");
            return variationOptionRepository.findByName(name);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:findByName to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public VariationOption saveModelDto(VariationOptionDto variationOptionDto) {
        try {
            log.info("VariationOptionService:saveModelDto execution started.");
            if (findByName(variationOptionDto.getName().trim()).isPresent()) {
                throw createValidationException("variationOptionDto", "name", THIS_FIELD_ALREADY_EXIST);
            }
            Variation variation = variationService.findById(variationOptionDto.getIdVariation())
                    .orElseThrow(() -> createValidationException("variationOptionDto", "idVariation", THIS_FIELD_CANNOT_EMPTY));
            VariationOption variationOption = variationOptionMapper.convertDtoToVariationOption(variationOptionDto);
            setPersonAction(variationOption);
            variationOption.setVariation(variation);
            setMetaData(variationOption);
            return variationOptionRepository.saveModel(variationOption);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting VariationOptionService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public QueryListResult<VariationOption> getListResult(String queryParameter) {
        try {
            log.info("VariationOptionService:getListResult execution started.");
            return variationOptionRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<VariationOption> findAll() {
        try {
            log.info("VariationOptionService:findAll execution started.");
            return variationOptionRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:findAll to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
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
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL.getCode());
        }
    }

    @Override
    public VariationOption updateModelDto(Long id, VariationOptionDto variationOptionDto) {
        try {
            log.info("VariationOptionService:updateModelDto execution started.");
            VariationOption variationOption = findById(id).get();
            if (findByName(variationOptionDto.getName().trim()).isPresent() && !variationOptionDto.getName().equals(variationOption.getName())) {
                throw createValidationException("variationOptionDto", "name", THIS_FIELD_ALREADY_EXIST);
            }
            Variation variation = variationService.findById(variationOptionDto.getIdVariation())
                    .orElseThrow(() -> createValidationException("variationOptionDto", "idVariation", THIS_FIELD_CANNOT_EMPTY));
            setPersonAction(variationOption);
            setMetaData(variationOption);
            variationOptionMapper.updateDtoToVariationOption(variationOptionDto, variationOption);
            variationOption.setVariation(variation);
            return variationOptionRepository.saveModel(variationOption);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL.getCode());
        }
    }
}
