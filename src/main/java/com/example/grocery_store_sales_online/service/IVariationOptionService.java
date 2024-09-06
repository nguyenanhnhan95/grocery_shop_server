package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.service.common.*;

public interface IVariationOptionService extends IFindByIdAble<VariationOption,Long>, ISaveModelDtoAble<VariationOption,VariationOptionDto>, IFindAllAble<VariationOption>,
        IFindByNameListAble<VariationOption>, IGetResultListAble<VariationOption>,IDeleteModelAble<Long>, IUpdateModelDtoAble<VariationOption,Long,VariationOptionDto> {

}
