package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ActiveException;
import com.example.grocery_store_sales_online.model.TestEntity;
import com.example.grocery_store_sales_online.service.file.IFileEntryService;
import com.example.grocery_store_sales_online.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private IFileEntryService fileEntryService;
    @PostMapping
    public ResponseEntity<?> saveTestData(@RequestBody TestEntity testEntity){
        if (testService.saveTest(testEntity)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping("/file-system")
    public ResponseEntity<?> uploadFileToSystem(@RequestParam("file") MultipartFile file){
        fileEntryService.saveFile(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping ("/file-system")
    public ResponseEntity<?> deleteFileToSystem(@RequestParam("id") Long id){
        fileEntryService.deleteModel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> test(){
        throw new ActiveException(EResponseStatus.ACCOUNT_NOT_ACTIVE);
    }
}
