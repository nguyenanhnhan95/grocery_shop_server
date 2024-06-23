package com.example.grocery_store_sales_online.service.promotion;

import com.example.grocery_store_sales_online.dto.shop.PromotionDto;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.repository.common.IGetListCode;
import com.example.grocery_store_sales_online.service.common.*;

public interface IPromotionService extends IDeleteModelAble<Long>, IFindAll<Promotion>, IFindByIdAble<Promotion,Long>
        ,IGetResultListAble<Promotion>, ISaveModelDtoAble<Promotion, PromotionDto>, IUpdateModelDtoAble<Promotion,Long,PromotionDto>,IFindByCodeAble<Promotion>, IGetListCode<Promotion> {
}
