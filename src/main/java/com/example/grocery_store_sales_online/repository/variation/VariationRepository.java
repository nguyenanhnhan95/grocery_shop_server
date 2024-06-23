package com.example.grocery_store_sales_online.repository.variation;
import com.example.grocery_store_sales_online.model.product.QVariation;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.projection.product.variation.VariationGetListResultProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class VariationRepository extends BaseRepository<Variation,Long> implements IVariationRepository {
    protected QVariation variation = QVariation.variation;
    public VariationRepository( EntityManager em) {
        super(Variation.class, em);
    }

    @Override
    public Optional<Variation> findByName(String name) {
        JPAQuery<Variation> jpaQuery = new JPAQuery<>(em);
        Variation result = jpaQuery.select(variation).from(variation)
                .where(variation.name.eq(name))
                .fetchOne();
        return Optional.ofNullable(result);
    }


    @Override
    public QueryListResult<Variation> getListResult(QueryParameter queryParameter) {
        JPAQuery<Variation> query = search(queryParameter.getCriterias());
        List<Variation> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<Variation>builder().result(result).total(total).build();
    }
    public JPAQuery<Variation> search(Map<String, Object> params) {
        String keyword = MapUtils.getString(params, "name");
        JPAQuery<Variation> variationJPAQuery = new JPAQuery<>(em);
        variationJPAQuery.select(Projections.constructor(Variation.class,variation.id, variation.name, variation.description)).from(variation);
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
            variationJPAQuery.where(variation.name.like(keyword));
        }
        return variationJPAQuery;
    }
}
