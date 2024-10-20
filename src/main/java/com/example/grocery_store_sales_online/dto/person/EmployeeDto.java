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

import static com.example.grocery_store_sales_online.utils.CommonConstants.REGEX_CCCD;


@Getter
@Setter
public class EmployeeDto extends PersonDto {
    @SizeStringConstraint(sizeMin = 4, sizeMax = 100)
    @Pattern(regexp = CommonConstants.REGEX_FULL_NAME,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    private String name;
    @SizeStringConstraint(sizeMin = 4, sizeMax = 70)
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    @NameLoginExistConstraint()
    private String nameLogin;
    @RegexConstraint(allowNull =true,regex = REGEX_CCCD)
    @IdCardConstraint()
    private String idCard;
    @SizeStringConstraint(sizeMin = 9, sizeMax = 11)
    @Pattern(regexp = CommonConstants.REGEX_PHONE, message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @PhoneExistConstraint()
    private String phone;
    @SizeStringConstraint(sizeMin = 6, sizeMax = 70)
    @Pattern(regexp = CommonConstants.REGEX_EMAIL,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @Email(message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @EmailExistConstraint()
    private String email;
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY)
    @SizeStringConstraint(sizeMin = 4, sizeMax = 70)
    private String password;
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY)
    @SizeStringConstraint(sizeMin = 6, sizeMax = 70)
    private String confirmPassword;

    @NotBlank(message = CommonConstants.THIS_COMBOBOX_ITEM_CANNOT_EMPTY)
    @AccountStatusConstraint()
    private String accountStatus;

    @RoleNotEmptyConstraint
    @RolesEmployeeConstraint
    private List<Long> roles;

}
