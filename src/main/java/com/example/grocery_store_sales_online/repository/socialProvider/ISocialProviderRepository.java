package com.example.grocery_store_sales_online.repository.socialProvider;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.model.person.SocialProvider;

import java.util.List;
import java.util.Optional;

public interface ISocialProviderRepository {
    Optional<SocialProvider> findByProviderAndIdProvider(AuthProvider authProvider, String idProvider);
//    List<SocialProvider> findByIdEmployee(Long idEmployee);
    List<SocialProvider> findByIdUser(Long idUser);
    Optional<SocialProvider> findByProviderId(String providerId);
}
