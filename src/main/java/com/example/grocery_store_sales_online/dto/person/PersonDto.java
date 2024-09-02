package com.example.grocery_store_sales_online.dto.person;

import com.example.grocery_store_sales_online.custom.validation.*;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Setter
@Getter
@AddressConstraint()
@PasswordConstraint()
public abstract class PersonDto {
    @FileTypeConstraint(contentType = {"image/png", "image/jpg", "image/jpeg"})
    @FileSizeConstraint(maxSize = CommonConstants.MAX_FILE_SIZE_IMAGE)
    private MultipartFile avatar;
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
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    private String password;
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    private String confirmPassword;
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
//    @Pattern(regexp = CommonConstants.REGEX_ADDRESS,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String address;
    @Size(max = 11,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 9,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_PHONE,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String phone;
    @Size(max = 70,message = CommonConstants.THIS_FIELD_TOO_LONG)
    @Size(min = 6,message = CommonConstants.THIS_FIELD_TOO_SHORT)
    @Pattern(regexp = CommonConstants.REGEX_EMAIL,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    @Email(message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String email;
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    @DateConstraint(value = 18, message = "Phải lớn hơn hoặc bằng 18 tuổi")
    private Date birthOfDate;
    @Pattern(regexp = CommonConstants.REGEX_CCCD,message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String idIdentification;
    @NotBlank(message = CommonConstants.THIS_COMBOBOX_ITEM_CANNOT_EMPTY)
    private String provinces;
    private String districts;
    private String wards;
}
