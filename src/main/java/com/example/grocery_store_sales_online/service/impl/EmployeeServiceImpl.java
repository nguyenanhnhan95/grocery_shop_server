package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;
import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.SendToAwsException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.custom.mapper.person.EmployeeMapper;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.repository.employee.impl.EmployeeRepository;
import com.example.grocery_store_sales_online.repository.user.IUserRepository;
import com.example.grocery_store_sales_online.service.*;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CommonUtils;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl extends BaseServiceImpl implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final IImageService imageService;
    private final ISocialProviderService socialProviderService;
    private final IUserRepository userRepository;
    @Override
    public List<Employee> findAllAble() {
        try {
            log.info("EmployeeService:findAll execution started.");
            return employeeRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findAll  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Employee saveModel(Employee employee) {
        try {
            log.info("EmployeeService:saveEmployee execution started.");
            setMetaData(employee);
            setPersonAction(employee);
            return employeeRepository.saveModel(employee);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:saveEmployee save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public Optional<Employee> findByName(String name) {
        try {
            log.info("EmployeeService:findByUserName execution started.");
            return employeeRepository.findByName(name);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findByUserName  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try {
            log.info("EmployeeService:findById execution started.");
            return employeeRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findById save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void deleteModel(Long aLong) {

    }

    @Override
    @Transactional
    public Employee saveModelDto(EmployeeDto model) {
        try {
            log.info("EmployeeService:saveModelDto execution started.");
            String keyAvatar = null;
            String idProvider = UUID.randomUUID().toString();
            if (model.getAvatar() != null) {
                try {
                    keyAvatar = imageService.handleImageAvatarToAws(model.getAvatar(), CommonUtils.generateNameAlias(model.getName()) + CommonConstants.UNDER_SCOPE + idProvider,null);
                } catch (Exception ex) {
                    throw new SendToAwsException(EResponseStatus.AWS_LOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_LOAD_IMAGE_FAIL.getCode());
                }
            }
            try {
                Employee employee = employeeMapper.convertEmployeeDtoToEmployee(model);
                employee.setAvatar(keyAvatar);
                setMetaData(employee);
                setPersonAction(employee);
                Employee saveEmployee = employeeRepository.saveModel(employee);
                this.createAndSaveSocialProvider(saveEmployee, idProvider);
                return saveEmployee;
            } catch (Exception ex) {
                throw ex;
            }
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
    public Employee updateModelDto(Long id, EmployeeEditDto employeeEditDto) {
        try {
            log.info("EmployeeService:updateModelDto execution started.");
            Optional<Employee> employee = this.findById(id);

            if (employee.isEmpty()) {
                throw createValidationException("employeeDto", "notification", CommonConstants.THIS_DATA_EDIT_FAIL);
            }
            String olderKey = employee.get().getAvatar();
            Employee employeeEdited = employeeMapper.updateEmployeeFromDto(employeeEditDto,employee.get());
            if (employeeEditDto.getAvatar() != null) {
                try {
                    employee.get().setAvatar(imageService.handleImageAvatarToAws(employeeEditDto.getAvatar(), CommonUtils.generateNameAlias(employeeEditDto.getName()) + CommonConstants.UNDER_SCOPE + System.currentTimeMillis(),olderKey));
                } catch (Exception ex) {
                    throw new SendToAwsException(EResponseStatus.AWS_LOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_LOAD_IMAGE_FAIL.getCode());
                }
            }
            this.processUpdate(employee.get(),employeeEditDto);

            setPersonAction(employeeEdited);
            setMetaData(employeeEdited);
            return employeeRepository.saveModel(employeeEdited);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL.getCode());
        }
    }
    private void processUpdate(Employee employee,EmployeeEditDto employeeEditDto){
        if(!employee.getNameLogin().equals(employeeEditDto.getNameLogin())){
            if(userRepository.findByNameLogin(employeeEditDto.getNameLogin()).isPresent() || employeeRepository.findByNameLogin(employeeEditDto.getNameLogin()).isPresent()){
                throw createValidationException("EmployeeEditDto","nameLogin",CommonConstants.THIS_FIELD_ALREADY_EXIST);
            }
            employee.setNameLogin(employeeEditDto.getNameLogin());
        }
        if(employeeEditDto.getEmail()!=null && !employeeEditDto.getEmail().isEmpty() && !employee.getEmail().equals(employeeEditDto.getEmail())){
            if(userRepository.findByEmail(employeeEditDto.getEmail()).isPresent() || employeeRepository.findByEmail(employeeEditDto.getEmail()).isPresent()){
                throw createValidationException("EmployeeEditDto","email",CommonConstants.THIS_FIELD_ALREADY_EXIST);
            }
            employee.setEmail(employeeEditDto.getEmail());
        }
        if(employeeEditDto.getIdCard()!=null && !employeeEditDto.getIdCard().isEmpty() && !employee.getIdCard().equals(employeeEditDto.getIdCard())){
            if(userRepository.findByIdCard(employeeEditDto.getIdCard()).isPresent() || employeeRepository.findByIdCard(employeeEditDto.getIdCard()).isPresent()){
                throw createValidationException("EmployeeEditDto","idCard",CommonConstants.THIS_FIELD_ALREADY_EXIST);
            }
            employee.setIdCard(employeeEditDto.getIdCard());
        }
        if(employeeEditDto.getPassword()!=null && !employeeEditDto.getPassword().isEmpty() && !employee.getPassword().equals(employeeEditDto.getPassword())){
            BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
            employee.setPassword(bCryptPasswordEncoder.encode(employeeEditDto.getPassword()));
        }
    }
    @Override
    public QueryListResult<EmployeeProjection> getListResult(String queryParameter) {
        try {
            log.info("EmployeeService:getListResult execution started.");
            return employeeRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public EmployeeProjection findByIdProjection(Long id) {
        try {
            log.info("EmployeeService:getListResult execution started.");
            Optional<EmployeeProjection> employee = employeeRepository.findByIdProjection(id);
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
    public void ChangeScreenMode(Long id, EScreenTheme screenTheme) {
        try {
            log.info("EmployeeService:ChangeScreenMode execution started.");
            Optional<Employee> employee = findById(id);
            employee.ifPresent(value -> {
                value.setScreenTheme(screenTheme);
                this.saveModel(value);
            });
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:ChangeScreenMode save to change screen mode , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getLabel(), EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getCode());
        }
    }

    @Override
    public Optional<Employee> findByNameLogin(String nameLogin) {
        try {
            log.info("EmployeeService:findByNameLogin execution started.");
            return employeeRepository.findByNameLogin(nameLogin);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findByNameLogin to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        try {
            log.info("EmployeeService:findByNameLogin execution started.");
            return employeeRepository.findByEmail(email);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findByNameLogin to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    private void createAndSaveSocialProvider(Employee employee, String providerId) {
        SocialProvider socialProvider = new SocialProvider();
        socialProvider.setProviderId(providerId);
        socialProvider.setProvider(AuthProvider.local);
        socialProvider.setEmployee(employee);

        try {
            socialProviderService.saveModel(socialProvider);
        } catch (Exception ex) {
            log.error("Failed to save SocialProvider, deleting Employee: {}", ex.getMessage());
            employeeRepository.delete(employee);
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
