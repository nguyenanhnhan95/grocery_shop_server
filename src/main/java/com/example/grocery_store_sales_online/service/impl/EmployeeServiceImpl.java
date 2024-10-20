package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;
import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.SendToAwsException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.custom.mapper.person.EmployeeMapper;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import com.example.grocery_store_sales_online.service.*;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CommonUtils;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl extends BaseServiceImpl implements IEmployeeService {
    private final EmployeeMapper employeeMapper;
    private final IImageService imageService;
    private final ISocialProviderService socialProviderService;
    private final UserRepository userRepository;

    @Value("${filestore.folder.image.avatar}")
    private String folderStoreAvatar;

    @Override
    public List<User> findAllAble() {
        try {
            log.info("EmployeeService:findAll execution started.");
            return userRepository.findAllEmployee();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findAll  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public User saveModel(User user) {
        try {
            log.info("EmployeeService:saveEmployee execution started.");
            setMetaData(user);
            setPersonAction(user);
            return userRepository.saveModel(user);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:saveEmployee save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public Optional<User> findByName(String name) {
//        try {
//            log.info("EmployeeService:findByUserName execution started.");
//            return employeeRepository.findByName(name);
//        } catch (Exception ex) {
//            log.error("Exception occurred while persisting EmployeeService:findByUserName  to database , Exception message {}", ex.getMessage());
//            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
//        }
        return null;
    }

    @Override
    public User findById(Long id) {
        log.info("EmployeeService:findById {} execution started.", id);
        try {
            return userRepository.findByIdEmployee(id)
                    .orElseThrow(() ->
                            new ServiceBusinessExceptional(
                                    EResponseStatus.NOT_FOUND_BY_ID.getLabel(),
                                    EResponseStatus.NOT_FOUND_BY_ID.getCode()
                            )
                    );
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findById save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void deleteModel(Long id) {
        try {
            log.info("EmployeeService:deleteModel execution started.");
            User employee = userRepository.findByIdEmployee(id)
                    .orElseThrow(() ->
                            new ServiceBusinessExceptional(
                                    EResponseStatus.NOT_FOUND_BY_ID.getLabel(),
                                    EResponseStatus.NOT_FOUND_BY_ID.getCode()
                            )
                    );
            employee.setDeleted(true);
            userRepository.saveModel(employee);
        } catch (ServiceBusinessExceptional ex) {
            log.error("Exception occurred while persisting EmployeeService:deleteModel save to database , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:deleteModel save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    @Transactional
    public User saveModelDto(EmployeeDto model) {
        try {
            log.info("EmployeeService:saveModelDto execution started.");
            String keyAvatar = null;
            String idProvider = UUID.randomUUID().toString();
            if (model.getAvatar() != null) {
                try {

                    keyAvatar = imageService.handleImageAvatarToAws(model.getAvatar(), CommonUtils.generateNameAlias(model.getName()) + CommonConstants.UNDER_SCOPE + idProvider, null);
                } catch (Exception ex) {
                    throw new SendToAwsException(EResponseStatus.AWS_LOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_LOAD_IMAGE_FAIL.getCode());
                }
            }
            User employee = employeeMapper.convertEmployeeDtoToEmployee(model);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
            employee.setAvatar(keyAvatar);
            setMetaData(employee);
            setPersonAction(employee);
            User saveUser = userRepository.saveModel(employee);
            this.createAndSaveSocialProvider(saveUser, idProvider);
            return this.saveModel(saveUser);
        } catch (SendToAwsException ex) {
            log.error("Exception occurred while persisting EmployeeService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    @Transactional
    public User updateModelDto(Long id, EmployeeEditDto employeeEditDto) {
        try {
            log.info("EmployeeService:updateModelDto execution started.");
            User employee = userRepository.findByIdEmployee(id)
                    .orElseThrow(() ->
                            createValidationException("employeeDto", "notification", CommonConstants.THIS_DATA_EDIT_FAIL)
                    );

//            String olderKey = employee.get().getAvatar();
            User employeeEdited = employeeMapper.updateEmployeeFromDto(employeeEditDto, employee);

            this.processUpdate(employee, employeeEditDto);

            setPersonAction(employeeEdited);
            setMetaData(employeeEdited);
            return userRepository.saveModel(employeeEdited);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL.getCode());
        }
    }

    private void processUpdate(User employee, EmployeeEditDto employeeEditDto) {
        if (employeeEditDto.getAvatar() != null) {
            try {
                employee.setAvatar(imageService.handleImageAvatarToAws(employeeEditDto.getAvatar(), CommonUtils.generateNameAlias(employeeEditDto.getName()) + CommonConstants.UNDER_SCOPE + System.currentTimeMillis(), employee.getAvatar()));
            } catch (Exception ex) {
                throw new SendToAwsException(EResponseStatus.AWS_LOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_LOAD_IMAGE_FAIL.getCode());
            }
        }
        if (!employee.getNameLogin().equals(employeeEditDto.getNameLogin())) {
            if (userRepository.findByNameLogin(employeeEditDto.getNameLogin()).isPresent()) {
                throw createValidationException("EmployeeEditDto", "nameLogin", CommonConstants.THIS_FIELD_ALREADY_EXIST);
            }
            employee.setNameLogin(employeeEditDto.getNameLogin());
        }
        if (employeeEditDto.getEmail() != null && !employeeEditDto.getEmail().isEmpty() && !employee.getEmail().equals(employeeEditDto.getEmail())) {
            if (userRepository.findByEmail(employeeEditDto.getEmail()).isPresent()) {
                throw createValidationException("EmployeeEditDto", "email", CommonConstants.THIS_FIELD_ALREADY_EXIST);
            }
            employee.setEmail(employeeEditDto.getEmail());
        }
        if (employeeEditDto.getIdCard() != null && !employeeEditDto.getIdCard().isEmpty() && !employee.getIdCard().equals(employeeEditDto.getIdCard())) {
            if (userRepository.findByIdCard(employeeEditDto.getIdCard()).isPresent()) {
                throw createValidationException("EmployeeEditDto", "idCard", CommonConstants.THIS_FIELD_ALREADY_EXIST);
            }
            employee.setIdCard(employeeEditDto.getIdCard());
        }
        String newPassword = employeeEditDto.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (newPassword != null && !newPassword.isEmpty() && !bCryptPasswordEncoder.matches(newPassword, employee.getPassword())) {
            // Chỉ mã hóa mật khẩu mới và cập nhật nếu nó khác mật khẩu cũ
            employee.setPassword(bCryptPasswordEncoder.encode(newPassword));
        }
    }

    @Override
    public QueryListResult<EmployeeProjection> getListResult(String queryParameter) {
        try {
            log.info("EmployeeService:getListResult execution started.");
            return userRepository.getListResultEmployee(readJsonQuery(queryParameter, QueryParameter.class));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public EmployeeProjection findByIdProjection(Long id) {
        try {
            log.info("EmployeeService:getListResult execution started.");
            Optional<EmployeeProjection> employee = userRepository.findByIdEmployeeProjection(id);
            if (employee.isPresent()) {
                return employee.get();
            }
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_DATA.getLabel(), EResponseStatus.NOT_FOUND_DATA.getCode());
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }


    @Override
    public Optional<User> findByEmail(String email) {
        try {
            log.info("EmployeeService:findByNameLogin execution started.");
            return userRepository.findByEmail(email);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findByNameLogin to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    private void createAndSaveSocialProvider(User user, String providerId) {
        SocialProvider socialProvider = new SocialProvider();
        socialProvider.setProviderId(providerId);
        socialProvider.setProvider(AuthProvider.local);
        socialProvider.setUser(user);

        try {
            socialProviderService.saveModel(socialProvider);
        } catch (Exception ex) {
            log.error("Failed to save SocialProvider, deleting Employee: {}", ex.getMessage());
            userRepository.delete(user);
            throw new ServiceBusinessExceptional(
                    EResponseStatus.SAVE_FAIL.getLabel(),
                    EResponseStatus.SAVE_FAIL.getCode()
            );
        }
    }

    private String folderUrl() {
        return getClass().getSimpleName().toLowerCase() + "/";
    }


}
