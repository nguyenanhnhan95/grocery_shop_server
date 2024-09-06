package com.example.grocery_store_sales_online.custom.mapper.person;

import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.model.person.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role convertDtoToRole(RoleDto roleDto);
    void updateDtoTo(RoleDto roleDto, @MappingTarget Role role);
}
