package com.example.grocery_store_sales_online.repository.employee;

import com.example.grocery_store_sales_online.model.TestEntity;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends BaseRepository<TestEntity,Integer> {
    public TestRepository(EntityManager em) {
        super(TestEntity.class,em);
    }


    public QueryListResult<TestEntity> findAll(QueryParameter queryParameter) {
        return null;
    }
}
