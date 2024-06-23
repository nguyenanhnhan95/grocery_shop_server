package com.example.grocery_store_sales_online.dto.product;


import com.example.grocery_store_sales_online.model.product.Variation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariationOptionDto {
    @Size(max = 150,message = "Độ dài nhập quá dài")
    @NotBlank(message = "Không để trống")
    @Pattern(regexp = "^([\\p{Lu}][\\p{Ll}]{1,8})(\\s([\\p{Lu}]|[\\p{Lu}][\\p{Ll}]{1,10})){0,5}$",message = "Nhập tùy chọn không phù hợp ")
    private String name;
    @Size(max = 250,message = "Độ dài nhập quá dài")
    private String description;
    @NotNull(message = "Lỗi chưa lựa chon option ")
    private Variation variation;
}
