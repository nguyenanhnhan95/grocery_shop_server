package com.example.grocery_store_sales_online.model.File;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Entity
@Table(name = "file_entry")
@Getter
@Setter
public class FileEntry extends Model {

    private String extension;

    private String fileUrl;

    // Đây là tên file đã chuyển sang dạng alias
    private String name;
    // Đây là tên gốc của file
    private String displayName;
    @Transient
    private MultipartFile content;
    @Transient
    public boolean isImage() {
        return extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg");
    }
    @Transient
    public boolean isVideo() {
        return extension.equals("mp4");
    }

}