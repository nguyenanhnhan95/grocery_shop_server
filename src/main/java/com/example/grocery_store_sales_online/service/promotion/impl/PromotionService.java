package com.example.grocery_store_sales_online.service.promotion.impl;

import com.example.grocery_store_sales_online.dto.shop.PromotionDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.mapper.PromotionMapper;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.repository.Promotion.PromotionRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.promotion.IPromotionService;
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
public class PromotionService extends BaseService implements IPromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    @Override
    public QueryListResult<Promotion> getListResult(String queryParameter) {
        return promotionRepository.getListResult(readJsonQuery(queryParameter));
    }

    @Override
    public void deleteModel(Long id) {
        try {
            log.info("PromotionService:deleteModel execution started.");
            promotionRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL);
        }
    }

    @Override
    public List<Promotion> findAll() {
        try {
            log.info("PromotionService:findAll execution started.");
            return promotionRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting promotionRepository.findAll() to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public Optional<Promotion> findById(Long id) {
        try {
            log.info("PromotionService:findById execution started.");
            return promotionRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public Promotion saveModelDto(PromotionDto promotionDto) {
        try {
            log.info("PromotionService:saveModelDto execution started.");
            Promotion promotion = promotionMapper.convertDtoToPromotion(promotionDto);
            if (findByCode(promotionDto.getCode().trim()).isPresent()) {
                BindingResult bindingResult = new BeanPropertyBindingResult(promotionDto, "promotionDto");
                bindingResult.addError(new FieldError("promotionDto", "code", "Mã code đã tồn tại"));
                throw new CustomValidationException(bindingResult, EResponseStatus.EXISTING);
            }
            setPersonAction(promotion);
            setMetaData(promotion);
            return promotionRepository.saveModel(promotion);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL);
        }
    }

    @Override
    public Promotion updateModelDto(Long id, PromotionDto promotionDto) {
        try {
            log.info("PromotionService:updateModelDto execution started.");
            Promotion promotion = findById(id).get();
            if (findByCode(promotionDto.getCode().trim()).isPresent()) {
                BindingResult bindingResult = new BeanPropertyBindingResult(promotionDto, "promotionDto");
                bindingResult.addError(new FieldError("promotionDto", "code", "Mã code đã tồn tại"));
                throw new CustomValidationException(bindingResult, EResponseStatus.EXISTING);
            }
            promotionMapper.updateDtoToPromotion(promotionDto, promotion);
            setPersonAction(promotion);
            setMetaData(promotion);
            return promotionRepository.saveModel(promotion);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL);
        }
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        try {
            log.info("PromotionService:findByCode execution started.");
            return promotionRepository.findByCode(code.trim());
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:findByCode to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public List<Promotion> getListCode() {
        try {
            log.info("PromotionService:getListCode execution started.");
            return promotionRepository.getListCode();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:getListCode to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }
}
