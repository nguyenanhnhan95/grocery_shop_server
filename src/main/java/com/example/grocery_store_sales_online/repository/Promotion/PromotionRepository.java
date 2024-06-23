package com.example.grocery_store_sales_online.repository.Promotion;

import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.model.shop.QPromotion;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PromotionRepository extends BaseRepository<Promotion, Long> implements IPromotionRepository {
    protected QPromotion promotion = QPromotion.promotion;

    public PromotionRepository(EntityManager em) {
        super(Promotion.class, em);
    }

    @Override
    public Optional<Promotion> findByName(String name) {
        JPAQuery<Promotion> jpaQuery = new JPAQuery<>(em);
        Promotion result = jpaQuery.select(promotion).from(promotion).where(promotion.name.eq(name)).fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public QueryListResult<Promotion> getListResult(QueryParameter queryParameter) {
        JPAQuery<Promotion> query = search(queryParameter.getCriterias());
        List<Promotion> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<Promotion>builder().result(result).total(total).build();
    }

    public JPAQuery<Promotion> search(Map<String, Object> params) {
        JPAQuery<Promotion> jpaQuery = new JPAQuery<>(em);
        jpaQuery.select(promotion).from(promotion);
        if (params != null && !params.isEmpty()) {
            String keyword = MapUtils.getString(params, "name");
            if (StringUtils.isNotBlank(keyword)) {
                keyword = "%" + keyword + "%";
                jpaQuery.where(promotion.name.like(keyword));
            }
            String code = MapUtils.getString(params, "code");
            if (StringUtils.isNotBlank(code)) {
                code = "%" + code + "%";
                jpaQuery.where(promotion.code.like(code));
            }
            Date startDate =stringToDate(MapUtils.getString(params, "startDate"));
            Date endDate = stringToDate(MapUtils.getString(params, "endDate"));
            if (startDate == null && endDate != null) {
                jpaQuery.where(promotion.endDate.eq(endDate));
            } else if (startDate != null) {
                if (endDate != null) {
                    jpaQuery.where(promotion.startDate.after(startDate).and(promotion.endDate.before(endDate)));
                } else {
                    jpaQuery.where(promotion.startDate.after(startDate));
                }
            }
        }
        return jpaQuery;
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        JPAQuery<Promotion> jpaQuery = new JPAQuery<>(em);
        Promotion result = jpaQuery.select(promotion).from(promotion)
                .where(promotion.code.eq(code)).fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Promotion> getListCode() {
        JPAQuery<Promotion> jpaQuery = new JPAQuery<>(em);
        List<Promotion> promotions = jpaQuery
                .select(Projections.constructor(Promotion.class,promotion.code))
                .from(promotion)
                .fetch();
        return promotions;
    }
}
