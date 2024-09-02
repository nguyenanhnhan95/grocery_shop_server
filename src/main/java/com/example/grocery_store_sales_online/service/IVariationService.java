package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.dto.product.VariationDto;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.service.common.*;

public interface IVariationService extends IFindByIdAble<Variation,Long>, ISaveModelDtoAble<Variation, VariationDto>,IFindAll<Variation>,
        IFindByNameListAble<Variation>, IGetResultListAble<Variation>, IDeleteModelAble<Long>, IUpdateModelDtoAble<Variation,Long,VariationDto> {
}
