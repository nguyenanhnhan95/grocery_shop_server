package com.example.grocery_store_sales_online.service.promotion.impl;

import com.example.grocery_store_sales_online.dto.shop.PromotionDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.mapper.product.PromotionMapper;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.repository.Promotion.impl.PromotionRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.promotion.IPromotionService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService extends BaseService implements IPromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    @Override
    public QueryListResult<Promotion> getListResult(String queryParameter) {
        try {
            log.info("PromotionService:getListResult execution started.");
            return promotionRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        }catch (Exception ex){
            log.error("Exception occurred while persisting promotionRepository.findAll() to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void deleteModel(Long id) {
        try {
            log.info("PromotionService:deleteModel execution started.");
            promotionRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL.getCode());
        }
    }

    @Override
    public List<Promotion> findAll() {
        try {
            log.info("PromotionService:findAll execution started.");
            return promotionRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting promotionRepository.findAll() to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<Promotion> findById(Long id) {
        try {
            log.info("PromotionService:findById execution started.");
            Optional<Promotion> promotion = promotionRepository.findById(id);
            if(promotion.isEmpty()){
                throw new  Exception(EResponseStatus.NOT_FOUND_BY_ID.getLabel());
            }
            return promotionRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_DATA.getLabel(), EResponseStatus.NOT_FOUND_DATA.getCode());
        }
    }

    @Override
    public Promotion saveModelDto(PromotionDto promotionDto) {
        try {
            log.info("PromotionService:saveModelDto execution started.");
            if (findByCode(promotionDto.getCode().trim()).isPresent()) {
                throw createValidationException("promotionDto", "code", THIS_FIELD_ALREADY_EXIST);
            }
            if (this.promotionRepository.findByName(promotionDto.getName()).isPresent()) {
                throw createValidationException("promotionDto", "name", THIS_FIELD_ALREADY_EXIST);
            }
            this.checkDateDto(promotionDto);
            Promotion promotion = promotionMapper.convertDtoToPromotion(promotionDto);
            setPersonAction(promotion);
            setMetaData(promotion);
            return promotionRepository.saveModel(promotion);
        } catch (CustomValidationException | ServiceBusinessExceptional ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public Promotion updateModelDto(Long id, PromotionDto promotionDto) {
        try {
            log.info("PromotionService:updateModelDto execution started.");
            Optional<Promotion> promotion = findById(id);
            promotion.orElseThrow(()-> createValidationException("promotionDto", "notification", THIS_FILED_DATA_NOT_EXIST));
            if (findByCode(promotionDto.getCode().trim()).isPresent() && !promotionDto.getCode().equals(promotion.get().getCode())) {
                throw createValidationException("promotionDto", "code", THIS_FIELD_ALREADY_EXIST);
            }
            this.checkDateDto(promotionDto);
            promotionMapper.updateDtoToPromotion(promotionDto, promotion.get());
            setPersonAction(promotion.get());
            setMetaData(promotion.get());
            return promotionRepository.saveModel(promotion.get());
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL.getCode());
        }
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        try {
            log.info("PromotionService:findByCode execution started.");
            return promotionRepository.findByCode(code.trim());
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:findByCode to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<Promotion> getListCode() {
        try {
            log.info("PromotionService:getListCode execution started.");
            return promotionRepository.getListCode();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:getListCode to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    private void checkDateDto(PromotionDto promotionDto){
        if(promotionDto.getStartDate()!=null){
            Date date =new Date();
            LocalDate dateCurrent = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
            LocalDate startDate = LocalDate.ofInstant(promotionDto.getStartDate().toInstant(), ZoneId.systemDefault());
            if (startDate.isBefore(dateCurrent)) {
                throw createValidationException("promotionDto", "startDate", THIS_FIELD_DATE_GREATER_EQUAL_DATE_CURRENT);
            }
        }
        if (promotionDto.getEndDate() != null) {
            if (promotionDto.getStartDate() == null || promotionDto.getStartDate().after(promotionDto.getEndDate())) {
                throw createValidationException("promotionDto", "endDate", "Ngày kết thúc phải lớn hơn ngày bắt đầu .");
            }
        }
    }
}
