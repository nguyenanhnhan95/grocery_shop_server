package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.AddressConstraint;
import com.example.grocery_store_sales_online.dto.person.PersonDto;
import com.example.grocery_store_sales_online.repository.address.impl.DistrictRepository;
import com.example.grocery_store_sales_online.repository.address.impl.ProvincesRepository;
import com.example.grocery_store_sales_online.repository.address.impl.WardRepository;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class AddressValidator extends BaseValidator implements ConstraintValidator<AddressConstraint, PersonDto> {
    private final ProvincesRepository provincesRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;

    @Override
    public boolean isValid(PersonDto dto, ConstraintValidatorContext context) {
        try {
            if (dto != null) {
                String provinces = dto.getProvinces();
                String districts = dto.getDistricts();
                String wards = dto.getWards();
                boolean isProvincesValid = StringUtils.isNotBlank(provinces);
                boolean isDistrictsValid = StringUtils.isNotBlank(districts);
                boolean isWardsValid = StringUtils.isNotBlank(wards);
                if (isProvincesValid) {
                    if (provincesRepository.findByCode(provinces).isEmpty()) {
                        this.addMessageValidation(context,"provinces",CommonConstants.THIS_FILE_ENTER_FAIL);
                        return false;
                    }
                    if (!isDistrictsValid) {
                        this.addMessageValidation(context,"districts");
                        if (isWardsValid) {
                            this.addMessageValidation(context,"districts",CommonConstants.PLEASE_COMPLETE_STEP);
                        } else {
                            this.addMessageValidation(context,"wards");
                        }
                        return false;
                    } else {
                        if (districtRepository.findByCode(districts).isEmpty()) {
                            this.addMessageValidation(context,"districts",CommonConstants.THIS_FILE_ENTER_FAIL);
                            return false;
                        }
                        if (!isWardsValid) {
                            this.addMessageValidation(context,"wards");
                            return false;
                        } else {
                            if (wardRepository.findByCode(wards).isEmpty()) {
                                this.addMessageValidation(context,"wards",CommonConstants.THIS_FILE_ENTER_FAIL);
                                return false;
                            }
                        }
                    }
                } else {
                    boolean isValid = true;

                    if (isDistrictsValid || isWardsValid) {
                        this.addMessageValidation(context, "provinces", CommonConstants.PLEASE_COMPLETE_STEP);
                        isValid = false;
                    }

                    if (isDistrictsValid && districtRepository.findByCode(districts).isEmpty()) {
                        this.addMessageValidation(context, "districts", CommonConstants.THIS_FILE_ENTER_FAIL);
                        isValid = false;
                    }

                    if (isWardsValid && wardRepository.findByCode(wards).isEmpty()) {
                        this.addMessageValidation(context, "wards", CommonConstants.THIS_FILE_ENTER_FAIL);
                        isValid = false;
                    }

                    return isValid;
                }
            }
            return true;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
    }

}
