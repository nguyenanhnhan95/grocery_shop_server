package com.example.grocery_store_sales_online.repository.variationOption;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.model.product.QVariation;
import com.example.grocery_store_sales_online.model.product.QVariationOption;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.projection.product.VariationOptionProjection;
import com.example.grocery_store_sales_online.projection.product.VariationProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class VariationOptionRepository extends BaseRepository<VariationOption, Long> implements IVariationOptionRepository {
    protected QVariationOption variationOption = QVariationOption.variationOption;
    protected QVariation variation = QVariation.variation;

    public VariationOptionRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(VariationOption.class, em,criteriaBuilderFactory);
    }

    @Override
    public Optional<VariationOption> findByName(String name) {
        BlazeJPAQuery<VariationOption> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        VariationOption result = jpaQuery.select(variationOption).from(variationOption)
                .where(variationOption.name.eq(name)).fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public QueryListResult<VariationOption> getListResult(QueryParameter queryParameter) {
        BlazeJPAQuery<VariationOption> query = search(queryParameter.getCriterias());
        List<VariationOption> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<VariationOption>builder().result(result).total(total).build();
    }

    public BlazeJPAQuery<VariationOption> search(Map<String, Object> params) {
        String keyword = MapUtils.getString(params, "name");
        BlazeJPAQuery<VariationOption> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        jpaQuery.select(variationOption).from(variationOption);
        if (params != null && !params.isEmpty()) {
            if (StringUtils.isNotBlank(keyword)) {
                keyword = "%" + keyword + "%";
                jpaQuery.where(variationOption.name.like(keyword));
            }
            try {
                Double id = (Double) MapUtils.getObject(params, "variation");
                if (id != null) {
                    jpaQuery.where(variationOption.variation.id.eq(id.longValue()));
                }
            }catch (Exception ex){
                log.error("Exception occurred while persisting VariationOptionRepository:search  , Exception message {}", ex.getMessage());
            }

        }
        return jpaQuery;
    }

    @Override
    public Optional<VariationOptionProjection> findByIdProjection(Long id) {
        BlazeJPAQuery<VariationOptionProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(Projections.constructor(VariationOptionProjection.class, variationOption.id, variationOption.name,
                        variationOption.description,
                        Projections.fields(VariationProjection.class,variation.id,variation.name,variation.description)))
                        .from(variationOption)
                .where(variationOption.id.eq(id)).fetchFirst());
    }
}
