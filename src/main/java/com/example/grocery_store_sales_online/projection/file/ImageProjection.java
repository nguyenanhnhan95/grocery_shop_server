package com.example.grocery_store_sales_online.projection.file;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageProjection {
    private Long id;
    private String description;
    private String extension;
    private int width;
    private int height;
    private String imageUrl="";
    private String medium="";
    private String small="";
    private String name="";

    public ImageProjection() {
    }
    @QueryProjection
    public ImageProjection(Long id, String imageUrl, String small) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.small = small;
    }
}
