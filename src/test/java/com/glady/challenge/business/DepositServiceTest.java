package com.glady.challenge.business;

import com.glady.challenge.repository.entity.CompanyEntity;
import com.glady.challenge.repository.entity.EmployeeEntity;
import com.glady.challenge.repository.entity.PaymentEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DepositService.class})
public class DepositServiceTest {

    public static final long EMPLOYEE_ID = 1L;
    public static final long COMPANY_ID = 11L;
    public static final long PAYMENT_ID = 111L;
    @MockBean
    private CommonService commonService;
    private DepositService depositService;

    @Before
    public void init() {
        when(commonService.getPaymentsByCompanyIdAndEmployeeId(COMPANY_ID, EMPLOYEE_ID)).thenReturn(List.of(buildPayment(PAYMENT_ID)));
        depositService = new DepositService(commonService);
    }

    @Test
    public void depositGift_CompanyAmountUpdatedTest() {
        // GIVEN
        BigDecimal companyBalance = new BigDecimal(10000);
        BigDecimal amount = new BigDecimal(200);
        EmployeeEntity employeeEntity = buildEmployeeAndCompany(EMPLOYEE_ID, COMPANY_ID, companyBalance);
        // WHEN
        when(commonService.findEntity(EmployeeEntity.class, EMPLOYEE_ID)).thenReturn(employeeEntity);
        depositService.depositGift(EMPLOYEE_ID, amount, COMPANY_ID);
        // THEN
        BigDecimal actualBalance = BigDecimal.valueOf(9800).setScale(2, RoundingMode.FLOOR);
        assertEquals(employeeEntity.getCompanyEntity().getBalance(), actualBalance);
    }

    @Test
    public void depositMeal_CompanyAmountUpdatedTest() {
        // GIVEN
        BigDecimal companyBalance = new BigDecimal(10000);
        BigDecimal amount = new BigDecimal(200);
        EmployeeEntity employeeEntity = buildEmployeeAndCompany(EMPLOYEE_ID, COMPANY_ID, companyBalance);
        // WHEN
        when(commonService.findEntity(EmployeeEntity.class, EMPLOYEE_ID)).thenReturn(employeeEntity);
        depositService.depositMeal(EMPLOYEE_ID, amount, COMPANY_ID);
        // THEN
        BigDecimal actualBalance = BigDecimal.valueOf(9800).setScale(2, RoundingMode.FLOOR);
        assertEquals(employeeEntity.getCompanyEntity().getBalance(), actualBalance);
    }
    private EmployeeEntity buildEmployeeAndCompany(Long employeeId, Long companyID, BigDecimal companyBalance) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);
        CompanyEntity companyEntity = buildCompany(companyID, companyBalance);
        employeeEntity.setCompanyEntity(companyEntity);
        return employeeEntity;
    }

    private CompanyEntity buildCompany(Long companyId, BigDecimal companyBalance) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyId);
        companyEntity.setBalance(companyBalance);
        return companyEntity;
    }

    private PaymentEntity buildPayment(Long paymentId) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(paymentId);
        return paymentEntity;
    }
}