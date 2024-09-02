package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.model.File.FileEntry;
import com.example.grocery_store_sales_online.service.common.IDeleteModelAble;
import com.example.grocery_store_sales_online.service.common.IFindAll;
import com.example.grocery_store_sales_online.service.common.IFindByIdAble;
import org.springframework.web.multipart.MultipartFile;

public interface IFileEntryService extends IFindAll<FileEntry>, IDeleteModelAble<Long>, IFindByIdAble<FileEntry,Long> {
    FileEntry saveFile(MultipartFile file,String directory);
    FileEntry saveFile(MultipartFile file);

}
