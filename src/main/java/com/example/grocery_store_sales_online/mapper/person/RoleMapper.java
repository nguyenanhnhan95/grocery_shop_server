package com.example.grocery_store_sales_online.mapper.person;

import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.model.person.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "permissions",target = "permissions")
    Role convertDtoToRole(RoleDto roleDto);
    void updateDtoTo(RoleDto roleDto, @MappingTarget Role role);
}
