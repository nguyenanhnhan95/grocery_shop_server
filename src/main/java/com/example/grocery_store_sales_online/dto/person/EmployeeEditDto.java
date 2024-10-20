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

import static com.example.grocery_store_sales_online.utils.CommonConstants.REGEX_CCCD;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeEditDto extends PersonDto{
    @SizeStringConstraint(sizeMin = 4, sizeMax = 100)
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    @Pattern(regexp = CommonConstants.REGEX_FULL_NAME,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String name;
    @SizeStringConstraint(sizeMin = 4, sizeMax = 70)
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    @Pattern(regexp = CommonConstants.REGEX_NAME_LOGIN,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String nameLogin;
    @Size(max = 11, message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 9, message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_PHONE, message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String phone;
    @RegexConstraint(allowNull =true,regex = REGEX_CCCD)
    private String idCard;
    @SizeStringConstraint(sizeMin = 6, sizeMax = 70)
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
