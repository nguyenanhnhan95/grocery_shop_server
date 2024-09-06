package com.example.grocery_store_sales_online.custom.mapper.person;

import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.address.Districts;
import com.example.grocery_store_sales_online.model.address.Provinces;
import com.example.grocery_store_sales_online.model.address.Wards;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.service.IAddressService;
import com.example.grocery_store_sales_online.service.IImageService;
import com.example.grocery_store_sales_online.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;


@Mapper(componentModel = "spring")
@Slf4j
public abstract class EmployeeMapper {
    @Autowired
    protected IRoleService roleService;

    @Autowired
    protected IAddressService addressService;

    @Mapping(source = "name", target = "name")
    @Mapping(source = "nameLogin", target = "nameLogin")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "idCard", target = "idCard")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "birthOfDate", target = "birthOfDate")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "avatar", target = "avatar",qualifiedByName = "mapAvatar")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRole")
    @Mapping(source = "provinces", target = "provinces", qualifiedByName = "mapProvinces")
    @Mapping(source = "districts", target = "districts", qualifiedByName = "mapDistricts")
    @Mapping(source = "wards", target = "wards", qualifiedByName = "mapWards")
    public abstract Employee convertEmployeeDtoToEmployee(EmployeeDto employeeDto);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "birthOfDate", target = "birthOfDate")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "avatar", target = "avatar",qualifiedByName = "mapAvatar")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRole")
    @Mapping(source = "provinces", target = "provinces", qualifiedByName = "mapProvinces")
    @Mapping(source = "districts", target = "districts", qualifiedByName = "mapDistricts")
    @Mapping(source = "wards", target = "wards", qualifiedByName = "mapWards")
    public abstract Employee updateEmployeeFromDto(EmployeeEditDto employeeEditDto, @MappingTarget Employee employee);

    @Named("mapAvatar")
    protected String mapAvatar(MultipartFile avatar){
        return null;
    }
    @Named("mapRole")
    protected Set<Role> mapRole(List<Long> roleId) {
        return roleService.listRoleByIDs(roleId);
    }


    @Named("mapAddress")
    protected <T> T mapAddress(String code, Function<String, T> mapperFunction) {
        try {
            return Optional.ofNullable(code)
                    .filter(c -> !c.isEmpty())
                    .map(mapperFunction)
                    .orElse(null);
        } catch (Exception ex) {
            log.error("Exception occurred mapper data, Exception message: {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CONVERT_MAPPER_FAIL.getLabel(), EResponseStatus.CONVERT_MAPPER_FAIL.getCode());
        }
    }
    @Named("mapProvinces")
    protected Provinces mapProvinces(String provinces) {
        return mapAddress(provinces, addressService::findByCodeProvinces);
    }

    @Named("mapDistricts")
    protected Districts mapDistricts(String districts) {
        return mapAddress(districts, addressService::findByCodeDistricts);
    }

    @Named("mapWards")
    protected Wards mapWards(String wards) {
        return mapAddress(wards, addressService::findByCodeWards);
    }

}
