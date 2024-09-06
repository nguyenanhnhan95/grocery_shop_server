package com.example.grocery_store_sales_online.dto.person;

import com.example.grocery_store_sales_online.custom.validation.*;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeEditDto extends PersonDto{
    @Size(max = 100,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 4,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    @Pattern(regexp = CommonConstants.REGEX_FULL_NAME,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String name;
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    @Pattern(regexp = CommonConstants.REGEX_NAME_LOGIN,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String nameLogin;
    @Size(max = 11, message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 9, message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_PHONE, message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String phone;
    @Pattern(regexp = CommonConstants.REGEX_CCCD,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String idCard;
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_EMAIL,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @Email(message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String email;
    @NotBlank(message = CommonConstants.THIS_COMBOBOX_ITEM_CANNOT_EMPTY)
    @AccountStatusConstraint()
    private String accountStatus;

    @RoleNotEmptyConstraint
    @RolesEmployeeConstraint
    private List<Long> roles;

}
