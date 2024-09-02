package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.address.Districts;
import com.example.grocery_store_sales_online.model.address.Provinces;
import com.example.grocery_store_sales_online.model.address.Wards;
import com.example.grocery_store_sales_online.projection.address.DistrictsProjection;
import com.example.grocery_store_sales_online.projection.address.ProvincesProjection;
import com.example.grocery_store_sales_online.projection.address.WardsProjection;
import com.example.grocery_store_sales_online.repository.address.impl.DistrictRepository;
import com.example.grocery_store_sales_online.repository.address.impl.ProvincesRepository;
import com.example.grocery_store_sales_online.repository.address.impl.WardRepository;
import com.example.grocery_store_sales_online.service.IAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl extends BaseServiceImpl implements IAddressService {
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final ProvincesRepository provincesRepository;

    @Override
    public List<WardsProjection> listWardsByCodeDistrict(String codeDistrict) {
        try {
            log.info("AddressService:listWardsByCodeDistrict execution started.");
            return wardRepository.listWardsByCodeDistrict(codeDistrict);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressService:listWardsByCodeDistrict to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<DistrictsProjection> listDistrictsByCodeProvince(String codeProvince) {
        try {
            log.info("AddressService:listDistrictsByCodeProvince execution started.");
            return districtRepository.listDistrictsByProvinceProjection(codeProvince);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressService:listDistrictsByCodeProvince to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<Provinces> findAllProvinces() {
        try {
            log.info("AddressService:findAllProvinces execution started.");
            return provincesRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressService:findAllProvinces to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public ProvincesProjection findByCodeProvinceProjection(String code) {
        try {
            log.info("AddressService:findByCodeProvince execution started.");
            Optional<ProvincesProjection> provinces = provincesRepository.findByCodeProjection(code);
            if (provinces.isPresent()) {
                return provinces.get();
            }
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_DATA.getLabel(), EResponseStatus.NOT_FOUND_DATA.getCode());
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressService:findByCodeProvince to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Districts findByCodeDistricts(String code) {
        try {
            log.info("AddressService:findByCodeDistricts execution started.");
            Optional<Districts> districts = districtRepository.findByCode(code);
            if(districts.isPresent()){
                return districts.get();
            }
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_DATA.getLabel(), EResponseStatus.NOT_FOUND_DATA.getCode());
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressService:findByCodeProvince to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Provinces findByCodeProvinces(String code) {
        try {
            log.info("AddressService:findByCodeProvinces execution started.");
            Optional<Provinces> provinces = provincesRepository.findByCode(code);
            if (provinces.isPresent()) {
                return provinces.get();
            }
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_DATA.getLabel(), EResponseStatus.NOT_FOUND_DATA.getCode());
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressService:findByCodeProvince to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Wards findByCodeWards(String code) {
        try {
            log.info("AddressService:findByCodeWards execution started.");
            Optional<Wards> wards = wardRepository.findByCode(code);
            if (wards.isPresent()) {
                return wards.get();
            }
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_DATA.getLabel(), EResponseStatus.NOT_FOUND_DATA.getCode());
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting AddressService:findByCodeProvince to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    /**
     * Tạo chuỗi string địa chỉ đầy đủ
     *
     * @param address
     * @param wards
     * @param districts
     * @param provinces
     * @return
     */
    public String getFullVietNamAddress(String address, Wards wards, Districts districts, Provinces provinces) {
        StringBuilder fullAddress = new StringBuilder();
        if (address != null && !address.isEmpty()) {
            fullAddress.append(address).append(",");
        }
        if (wards != null) {
            fullAddress.append(" ")
                    .append(wards.getAdministrativeUnits().getShort_name())
                    .append(" ")
                    .append(wards.getName())
                    .append(",");
        }
        if (districts != null) {
            fullAddress.append(" ")
                    .append(districts.getAdministrativeUnits().getShort_name())
                    .append(" ")
                    .append(districts.getName())
                    .append(",");
        }
        if (provinces != null) {
            fullAddress.append(" ")
                    .append(provinces.getAdministrativeUnits().getShort_name())
                    .append(" ")
                    .append(provinces.getName());
        }

        return fullAddress.toString();
    }
}
