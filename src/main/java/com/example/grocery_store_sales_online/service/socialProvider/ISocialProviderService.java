package com.example.grocery_store_sales_online.service.socialProvider;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.service.common.IDeleteModelAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;

import java.util.List;
import java.util.Optional;

public interface ISocialProviderService extends ISaveModelAble<SocialProvider>, IDeleteModelAble<Long> {
    Optional<SocialProvider> findByProviderAndIdProvider(AuthProvider authProvider,String idProvider);
    List<SocialProvider> findByIdEmployee(Long idEmployee);
    List<SocialProvider> findByIdUser(Long idUser);
    Optional<SocialProvider> findByProviderId(String providerId);
}
