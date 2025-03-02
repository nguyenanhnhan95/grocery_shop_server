package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.dto.product.VariationDto;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.projection.product.VariationProjection;
import com.example.grocery_store_sales_online.service.common.*;

public interface IVariationService extends IFindByIdAble<Variation,Long>, ISaveModelDtoAble<Variation, VariationDto>, IFindAllAble<Variation>,
        IFindByNameListAble<Variation>, IGetResultListAble<VariationProjection>, IDeleteModelAble<Long>, IUpdateModelDtoAble<Variation,Long,VariationDto> {
}
