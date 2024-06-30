package com.example.grocery_store_sales_online.model.File;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Entity
@Table(name = "image")
@Getter
@Setter
public class Image extends Model {
    public static transient final Logger LOG = LogManager.getLogger(Image.class.getName());
    private String description;
    private String extension;
    private int width;
    private int height;
    private String imageUrl="";
    private String medium="";
    private String small="";
    private String name="";
    private boolean publishStatus;
    public Image(){};

}
