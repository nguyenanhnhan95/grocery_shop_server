package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.custom.mapper.product.VariationOptionMapper;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.projection.product.VariationOptionProjection;
import com.example.grocery_store_sales_online.repository.variation.VariationRepository;
import com.example.grocery_store_sales_online.repository.variationOption.VariationOptionRepository;
import com.example.grocery_store_sales_online.service.IVariationOptionService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariationOptionServiceImpl extends BaseServiceImpl implements IVariationOptionService {
    private final VariationOptionRepository variationOptionRepository;
    private final VariationOptionMapper variationOptionMapper;
    private final VariationRepository variationRepository;

    @Override
    public VariationOption findById(Long id) {
        log.info("VariationOptionService:findById execution started.");
        try {
            return variationOptionRepository.findById(id)
                    .orElseThrow(() ->
                            new ServiceBusinessExceptional(
                                    EResponseStatus.NOT_FOUND_BY_ID.getLabel(),
                                    EResponseStatus.NOT_FOUND_BY_ID.getCode()
                            )
                    );
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting VariationOptionService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    @Override
    public VariationOptionProjection findByIdProjection(Long id) {
        log.info("VariationOptionService:findByIdAndRelationshipAble execution started.");
        try {
            return variationOptionRepository.findByIdProjection(id)
                    .orElseThrow(() ->
                            new ServiceBusinessExceptional(
                                    EResponseStatus.NOT_FOUND_BY_ID.getLabel(),
                                    EResponseStatus.NOT_FOUND_BY_ID.getCode()
                            )
                    );
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
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
            Variation variation = variationRepository.findById(variationOptionDto.getIdVariation())
                    .orElseThrow(() -> createValidationException("variationOptionDto", "idVariation", THIS_FIELD_CANNOT_EMPTY));
            VariationOption variationOption = variationOptionMapper.convertDtoToVariationOption(variationOptionDto);
            setPersonAction(variationOption);
            variationOption.setVariation(variation);
            setMetaData(variationOption);
            return variationOptionRepository.saveModel(variationOption);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
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
    public List<VariationOption> findAllAble() {
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
            variationOptionRepository.findById(id)
                    .orElseThrow(() ->
                            new ServiceBusinessExceptional(
                                    EResponseStatus.NOT_FOUND_BY_ID.getLabel(),
                                    EResponseStatus.NOT_FOUND_BY_ID.getCode()
                            )
                    );
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
            VariationOption variationOption = variationOptionRepository.findById(id)
                    .orElseThrow(() ->
                            createValidationException("variationOptionDto", "notification", THIS_DATA_EDIT_FAIL)
                    );
            if (findByName(variationOptionDto.getName().trim()).isPresent() && !variationOptionDto.getName().equals(variationOption.getName())) {
                throw createValidationException("variationOptionDto", "name", THIS_FIELD_ALREADY_EXIST);
            }
            Variation variation = variationRepository.findById(variationOptionDto.getIdVariation())
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
