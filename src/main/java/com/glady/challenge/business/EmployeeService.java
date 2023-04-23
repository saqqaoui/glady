package com.glady.challenge.business;

import com.glady.challenge.business.model.DepositType;
import com.glady.challenge.business.service.IEmployeeService;
import com.glady.challenge.repository.entity.EmployeeEntity;
import com.glady.challenge.repository.entity.PaymentEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    private CommonService commonService;

    public EmployeeService(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public BigDecimal calculateEmployeeBalance(Long employeeId) {
        EmployeeEntity employeeEntity = (EmployeeEntity) commonService.findEntity(EmployeeEntity.class, employeeId);
        commonService.checkCompanyAuthorizationOnEmployee(employeeEntity, null);

        List<PaymentEntity> allPaymentEntities = commonService.getPaymentsByCompanyIdAndEmployeeId(employeeEntity.getCompanyEntity().getId(), employeeId);
        return allPaymentEntities.stream()
                .filter(payment -> !expired(payment))
                .map(PaymentEntity::getAmount)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
    private static boolean expired(PaymentEntity paymentEntity) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(paymentEntity.getCreatedAt());
        if (DepositType.GIFT.name().equals(paymentEntity.getDepositType())) {
            cal.add(Calendar.YEAR, 1);
        } else {
            cal.add(Calendar.YEAR, 1);
            cal.set(Calendar.MONTH, Calendar.FEBRUARY);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return cal.getTime().before(Calendar.getInstance().getTime());
    }

}
