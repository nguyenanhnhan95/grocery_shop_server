package com.example.grocery_store_sales_online.repository.base;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.Fetchable;
import com.querydsl.core.SimpleQuery;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
@NoRepositoryBean
public abstract class BaseRepository <T,ID> extends SimpleJpaRepository<T,ID> implements IBaseRepository<T,ID>{
    private static final Logger logger = LoggerFactory.getLogger(BaseRepository.class);
    @PersistenceContext
    protected EntityManager em;

    protected EntityInformation<T,ID> entityInformation;
    protected CriteriaBuilderFactory criteriaBuilderFactory;

    public BaseRepository(Class<T> domainClass, EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(domainClass, em);
        this.em = em;
        this.criteriaBuilderFactory = criteriaBuilderFactory;
    }
    @Transactional
    public T saveModel(final T obj) {
        T a = (T) obj;
       return save((T) obj);
    }
    @Override
    public Optional<T> findByIdMandatory(ID id) {
        return Optional.ofNullable(findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found")));
    }
    @Override
    public void clear(){
        em.clear();
    }
    @Override
    public void detach(T entity){
        em.detach(entity);
    }

    @SuppressWarnings("unchecked")
    public <B, A extends SimpleQuery<?> & Fetchable<B>> A page(final A qry, int size, int page) {
        if (size < 0) { // không giới hạn
            return qry;
        }

        long count;

        // Kiểm tra nếu qry là một BlazeJPAQuery
        if (qry instanceof BlazeJPAQuery<?> blazeQuery) {
            // Lấy số lượng kết quả
            count = blazeQuery.fetchCount();

            // Nếu có group by, cần phải điều chỉnh cách tính count
            if (!blazeQuery.getMetadata().getGroupBy().isEmpty()) {
                Expression<?> pro = blazeQuery.getMetadata().getProjection();
                if (pro == null) {
                    pro = blazeQuery.getMetadata().getJoins().get(0).getTarget();
                }
                count = blazeQuery.select(Expressions.ZERO).fetch().size();
                blazeQuery.select(pro);
            }
        } else {
            // Nếu không phải là BlazeJPAQuery, dùng fetchCount()
            count = qry.fetchCount();
        }

        // Nếu số lượng kết quả ít hơn hoặc bằng số lượng tối đa cho trang hiện tại, đặt lại trang về 0
        if (count <= (long) page * size) {
            page = 0;
        }

        // Trả về truy vấn với offset và limit
        return (A) qry.offset((long) page * size).limit(size);
    }
    protected Date stringToDate(String str){
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME);
            return Date.from(zonedDateTime.toInstant());
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }

    }

}
