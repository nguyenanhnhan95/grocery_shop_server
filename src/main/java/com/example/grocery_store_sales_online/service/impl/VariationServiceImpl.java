package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.product.VariationDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.custom.mapper.product.VariationMapper;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.repository.variation.IVariationJpaRepository;
import com.example.grocery_store_sales_online.repository.variation.VariationRepository;
import com.example.grocery_store_sales_online.service.IVariationService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariationServiceImpl extends BaseServiceImpl implements IVariationService {
    private final VariationRepository variationRepository;
    private final IVariationJpaRepository variationJpaRepository;
    private final VariationMapper variationMapper;

    @Override
    public Optional<Variation> findById(Long id) {
        try {
            log.info("VariationService:findById execution started.");
            return variationRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<Variation> findByName(String name) {
        try {
            log.info("VariationService:findByName execution started.");
            return variationRepository.findByName(name);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationService:findByName to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public QueryListResult<Variation> getListResult(String queryParameter) {
        try {
            log.info("VariationService:getListResult execution started.");
            return variationRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }

    }

    @Override
    public Variation saveModelDto(VariationDto variationDto) {
        try {
            log.info("VariationService:saveModelDto execution started.");
            if (findByName(variationDto.getName().trim()).isPresent()) {
                throw createValidationException("variationDto", "name", THIS_FIELD_ALREADY_EXIST);
            }
            Variation variation = variationMapper.convertVariationDtoToVariation(variationDto);
            setPersonAction(variation);
            setMetaData(variation);
            return variationRepository.saveModel(variation);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }


    @Override
    public void deleteModel(Long id) {
        try {
            log.info("VariationService:deleteModel execution started.");
            variationRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL.getCode());
        }
    }

    @Override
    public Variation updateModelDto(Long id, VariationDto variationDto) {
        try {
            log.info("VariationService:updateModelDto execution started.");
            Variation variation = findById(id).get();
            if (findByName(variationDto.getName().trim()).isPresent() && !variationDto.getName().equals(variation.getName())) {
                throw createValidationException("variationDto", "name", THIS_FIELD_ALREADY_EXIST);
            }
            setPersonAction(variation);
            setMetaData(variation);
            variationMapper.updateVariationFromDto(variationDto, variation);
            return variationRepository.saveModel(variation);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL.getCode());
        }
    }

    @Override
    public List<Variation> findAllAble() {
        try {
            log.info("VariationService:findAll execution started.");
            return variationRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationService:findAll to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

}
