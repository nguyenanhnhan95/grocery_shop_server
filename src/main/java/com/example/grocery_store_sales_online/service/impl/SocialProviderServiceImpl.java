package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.repository.socialProvider.impl.SocialProviderRepository;
import com.example.grocery_store_sales_online.service.ISocialProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialProviderServiceImpl extends BaseServiceImpl implements ISocialProviderService {
    private final SocialProviderRepository socialProviderRepository;
    @Override
    public Optional<SocialProvider> findByProviderAndIdProvider(AuthProvider authProvider, String idProvider) {
        try {
            log.info("SocialProviderService:findByProviderAndIdProvider execution started.");
            return socialProviderRepository.findByProviderAndIdProvider(authProvider,idProvider);
        }catch (Exception ex){
            log.error("Exception occurred while persisting SocialProviderService:findByProviderAndIdProvider to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<SocialProvider> findByIdEmployee(Long idEmployee) {
        try {
            log.info("SocialProviderService:findByIdEmployee execution started.");
            return socialProviderRepository.findByIdEmployee(idEmployee);
        }catch (Exception ex){
            log.error("Exception occurred while persisting SocialProviderService:findByIdEmployee to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<SocialProvider> findByIdUser(Long idUser) {
        try {
            log.info("SocialProviderService:findByIdUser execution started.");
            return socialProviderRepository.findByIdUser(idUser);
        }catch (Exception ex){
            log.error("Exception occurred while persisting SocialProviderService:findByIdUser to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<SocialProvider> findByProviderId(String providerId) {
        try {
            log.info("SocialProviderService:findByProviderId execution started.");
            return socialProviderRepository.findByProviderId(providerId);
        }catch (Exception ex){
            log.error("Exception occurred while persisting SocialProviderService:findByProviderId to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public SocialProvider saveModel(SocialProvider model) {
        try {
            log.info("SocialProviderService:saveModel execution started.");
            Optional<SocialProvider> socialProvider = this.findByProviderAndIdProvider(model.getProvider(), model.getProviderId());
            if(socialProvider.isPresent()){
                return socialProvider.get();
            }
            setMetaData(model);
            setPersonAction(model);
            return socialProviderRepository.saveModel(model);
        }catch (Exception ex){
            log.error("Exception occurred while persisting SocialProviderService:saveModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public void deleteModel(Long id) {
        try {
            log.info("SocialProviderService:deleteModel execution started.");
            socialProviderRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting SocialProviderService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL.getCode());
        }
    }
}
