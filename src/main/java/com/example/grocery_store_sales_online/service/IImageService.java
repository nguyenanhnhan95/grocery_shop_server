package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService extends ISaveModelAble<Image> {
    Image handleToImage(MultipartFile file, String directory);
    List<Image> handleToImages(List<MultipartFile> files, String directory);
    String saveImage(MultipartFile image);
    String handleImageToServerByUrl(String url ,String directory);
    String handleImageAvatarToAws(MultipartFile image,String directory,String olderKey);
}
