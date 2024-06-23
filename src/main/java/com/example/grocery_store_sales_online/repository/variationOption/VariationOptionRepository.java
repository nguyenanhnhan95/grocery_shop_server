package com.example.grocery_store_sales_online.repository.variationOption;

import com.example.grocery_store_sales_online.model.product.QVariationOption;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.jpa.impl.JPAQuery;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class VariationOptionRepository extends BaseRepository<VariationOption, Long> implements IVariationOptionRepository {
    protected QVariationOption variationOption = QVariationOption.variationOption;

    public VariationOptionRepository(EntityManager em) {
        super(VariationOption.class, em);
    }

    @Override
    public Optional<VariationOption> findByName(String name) {
        JPAQuery<VariationOption> jpaQuery = new JPAQuery<>(em);
        VariationOption result = jpaQuery.select(variationOption).from(variationOption)
                .where(variationOption.name.eq(name)).fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public QueryListResult<VariationOption> getListResult(QueryParameter queryParameter) {
        JPAQuery<VariationOption> query = search(queryParameter.getCriterias());
        List<VariationOption> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<VariationOption>builder().result(result).total(total).build();
    }

    public JPAQuery<VariationOption> search(Map<String, Object> params) {
        String keyword = MapUtils.getString(params, "name");
        JPAQuery<VariationOption> jpaQuery = new JPAQuery<>(em);
        jpaQuery.select(variationOption).from(variationOption);
        if (params != null && !params.isEmpty()) {
            if (StringUtils.isNotBlank(keyword)) {
                keyword = "%" + keyword + "%";
                jpaQuery.where(variationOption.name.like(keyword));
            }
            Double id = (Double) MapUtils.getObject(params,"variation");
            if(id!=null){
                jpaQuery.where(variationOption.variation.id.eq(id.longValue()));
            }
        }
        return jpaQuery;
    }
}
