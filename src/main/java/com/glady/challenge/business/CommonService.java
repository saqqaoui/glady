package com.glady.challenge.business;

import com.glady.challenge.integration.CommonDAO;
import com.glady.challenge.exception.CompanyPaymentAuthorizationException;
import com.glady.challenge.exception.EmployeeHasNoCompanyException;
import com.glady.challenge.exception.UnknownEntityException;
import com.glady.challenge.repository.entity.EmployeeEntity;
import com.glady.challenge.repository.entity.PaymentEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class CommonService {

    private CommonDAO commonDAO;

    public CommonService(CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public void checkCompanyAuthorizationOnEmployee(EmployeeEntity employeeEntity, Long requestCompanyId) {
        if(employeeEntity.getCompanyEntity() == null) {
            throw new EmployeeHasNoCompanyException(employeeEntity.getId());
        }
        if(requestCompanyId != null && !employeeEntity.getCompanyEntity().getId().equals(requestCompanyId)) {
            throw new CompanyPaymentAuthorizationException(requestCompanyId);
        }
    }

    public List<PaymentEntity> getPaymentsByCompanyIdAndEmployeeId(Long companyID, Long employeeId) {
        return commonDAO.findPaymentsByCompanyIdAndEmployeeId(companyID, employeeId);
    }

    protected <T> Object findEntity(Class<T> clazz, Long entityId) {
        Object entity = commonDAO.find(clazz, entityId);
        if (Optional.ofNullable(entity).isEmpty()) {
            throw new UnknownEntityException(clazz.getSimpleName(), entityId);
        }
        return entity;
    }
    public void createOrUpdate(Object... objects) {
        for (Object object : objects) {
            commonDAO.createOrUpdate(object);
        }
    }
}
