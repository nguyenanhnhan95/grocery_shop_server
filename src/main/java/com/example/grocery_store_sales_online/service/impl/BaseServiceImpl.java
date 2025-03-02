package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.ProcessImage;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.google.gson.Gson;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.example.grocery_store_sales_online.utils.CommonConstants.MAX_FILE_SIZE;
import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_NOT_CORRECT_FORMAT;

@Slf4j
public class BaseServiceImpl {
    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_PAGE = 0;
    @Getter
    @Setter
    private @Nullable Date fromDate;
    @Getter
    @Setter
    private @Nullable Date toDate;
    @Getter
    @Setter
    private QueryParameter queryParameter = QueryParameter.builder().size(DEFAULT_SIZE).page(DEFAULT_PAGE).build();
    private static final List<Integer> PAGE_SIZE_OPTIONS = Arrays.asList(5, 10, 20, 30, 50, 70, 100);

    public List<Integer> getPageSizeOptions() {
        return PAGE_SIZE_OPTIONS;
    }

    public List<Integer> getListPageSizes() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(50);
        list.add(100);
        return list;
    }

    public @Nullable Date getFixToDate() {
        Date fixToDate = toDate;
        if (fixToDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fixToDate);
            //cal.add(Calendar.DATE, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            fixToDate = cal.getTime();
        }
        return fixToDate;
    }

    public @Nullable Date getFixFromDate() {
        Date fixFromDate = fromDate;
        if (getToDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fixFromDate);
            //cal.add(Calendar.DATE, 1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            fixFromDate = cal.getTime();
        }
        return fixFromDate;
    }

    protected void setMetaData(Model obj) {
        if (obj.getCreateDate() == null) {
            obj.setCreateDate(new Date());
        }
        obj.setEditDate(new Date());
    }

    protected <E> E readJsonQuery(String queryParameter,Class<E> modelClass) {
        try {
            log.info("BaseService:readJsonQuery execution started.");
            Gson g = new Gson();
            return g.fromJson(queryParameter, modelClass);
        }catch (Exception ex){
            log.error("Exception occurred while persisting BaseService:readJsonQuery to read JsonQuery , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    protected UserPrincipal getCurrentUser() {
        try {
            log.info("BaseService:getCurrentUser execution started.");
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserPrincipal userPrincipal=null;
            if (principal instanceof UserDetails) {
                userPrincipal = (UserPrincipal) principal;
            }
            return userPrincipal;
        }catch (Exception ex){
            log.error("Exception occurred BaseService:getCurrentUser not authentication , Exception message {} ",ex.getMessage());
            return null;
        }
    }

    protected void setPersonAction(Model model){
        UserPrincipal userPrincipal =getCurrentUser();
        if(userPrincipal!=null){
            if(model.getPersonCreate()!=null){
                model.setPersonEdit(userPrincipal.getName());
            }else{
                model.setPersonCreate(userPrincipal.getName());
            }
        }
    }

    protected CustomValidationException createValidationException(String objectName, String field, String message) {
        BindingResult bindingResult = new BeanPropertyBindingResult(objectName, objectName);
        bindingResult.addError(new FieldError(objectName, field, message));
        return new CustomValidationException(bindingResult, EResponseStatus.EXISTING.getCode());
    }
    protected void validateImage(String nameObject, String field, MultipartFile image){
        if (image == null || !ProcessImage.checkExtensionImage(image.getOriginalFilename())) {
            throw createValidationException(nameObject, field, THIS_FIELD_NOT_CORRECT_FORMAT);
        }
        if (image.getSize() > MAX_FILE_SIZE) {
            throw createValidationException(nameObject, field, CommonConstants.THIS_FILE_SIZE_TOO_LARGE);
        }
    }
    protected void validateImages(String nameObject, String field, List<MultipartFile> images){
        for (MultipartFile image: images) {
            if (image == null || !ProcessImage.checkExtensionImage(image.getOriginalFilename())) {
                throw createValidationException(nameObject, field, THIS_FIELD_NOT_CORRECT_FORMAT);
            }
            if (image.getSize() > MAX_FILE_SIZE) {
                throw createValidationException(nameObject, field, CommonConstants.THIS_FILE_SIZE_TOO_LARGE);
            }
        }

    }
}
