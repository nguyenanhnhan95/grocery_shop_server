package com.example.grocery_store_sales_online.dto.person;

import com.example.grocery_store_sales_online.custom.validation.*;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
public class EmployeeDto extends PersonDto {
    @Size(max = 100,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 4,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_FULL_NAME,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    private String name;
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    @NameLoginExistConstraint()
    private String nameLogin;
    @Pattern(regexp = CommonConstants.REGEX_CCCD,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @IdCardConstraint()
    private String idCard;
    @Size(max = 11, message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 9, message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_PHONE, message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @PhoneExistConstraint()
    private String phone;
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_EMAIL,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @Email(message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @EmailExistConstraint()
    private String email;
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY)
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    private String password;
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY)
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    private String confirmPassword;

    @NotBlank(message = CommonConstants.THIS_COMBOBOX_ITEM_CANNOT_EMPTY)
    @AccountStatusConstraint()
    private String accountStatus;

    @RoleNotEmptyConstraint
    @RolesEmployeeConstraint
    private List<Long> roles;

}
