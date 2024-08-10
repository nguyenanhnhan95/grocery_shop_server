package com.example.grocery_store_sales_online.service.image;

import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService extends ISaveModelAble<Image> {
    Image hanldeToImage(MultipartFile file,String directory);
    String saveImage(MultipartFile image);
}
