package com.example.grocery_store_sales_online.service.test;

import com.example.grocery_store_sales_online.model.TestEntity;
import com.example.grocery_store_sales_online.repository.employee.TestRepository;
import com.example.grocery_store_sales_online.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService extends BaseService {
    @Autowired
    private TestRepository testRepository;
    public boolean saveTest(TestEntity testEntity){
        try {
            setMetaData(testEntity);
            testRepository.save(testEntity);
            return true;
        }catch (Exception  e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
