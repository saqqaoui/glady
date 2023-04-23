package com.glady.challenge.integration;

import com.glady.challenge.business.CommonService;
import com.glady.challenge.business.model.DepositType;
import com.glady.challenge.exception.CompanyPaymentAuthorizationException;
import com.glady.challenge.exception.EmployeeHasNoCompanyException;
import com.glady.challenge.repository.entity.CompanyEntity;
import com.glady.challenge.repository.entity.EmployeeEntity;
import com.glady.challenge.repository.entity.PaymentEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class CommonServiceIT {

    public static final long FIRST_COMPANY_ID = 1L;
    public static final long SECOND_COMPANY_ID = 2L;
    public static final long FIRST_EMPLOYEE_ID = 1L;
    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private CommonService commonService;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    @Before
    public void setup() {
        EmployeeEntity employeeEntity = buildEmployeeEntity(FIRST_EMPLOYEE_ID, FIRST_COMPANY_ID);
        CompanyEntity companyEntity = buildCompanyEntity(FIRST_COMPANY_ID);
        commonDAO.createOrUpdate(companyEntity);
        commonDAO.createOrUpdate(employeeEntity);
        commonService = new CommonService(commonDAO);

        LocalDate currentDate = LocalDate.now();
        currentYear = currentDate.getYear();
        currentMonth = currentDate.getMonthValue();
        currentDay =currentDate.getDayOfMonth();

    }

    public EmployeeEntity checkAndReturnIfEmployeeExistsInMemory() {
        EmployeeEntity e = (EmployeeEntity) commonDAO.find(EmployeeEntity.class, FIRST_EMPLOYEE_ID);
        assertNotNull(e);
        return e;
    }

    public CompanyEntity checkAndReturnIfCompanyExistsInMemory() {
        CompanyEntity c = (CompanyEntity) commonDAO.find(CompanyEntity.class, FIRST_COMPANY_ID);
        assertNotNull(c);
        return c;
    }
    @Test
    public void checkPaymentExistsAndValidInMemory() {
        EmployeeEntity e = checkAndReturnIfEmployeeExistsInMemory();
        CompanyEntity c = checkAndReturnIfCompanyExistsInMemory();
        BigDecimal firstAmount = new BigDecimal(300.82);
        PaymentEntity firstPaymentEntity = buildPaymentEntity(e, c, DepositType.GIFT, firstAmount);
        BigDecimal secondAmount = new BigDecimal(20.23);
        PaymentEntity secondPaymentEntity = buildPaymentEntity(e, c, DepositType.MEAL, secondAmount);
        commonService.createOrUpdate(firstPaymentEntity, secondPaymentEntity);

        List<PaymentEntity> res = commonService.getPaymentsByCompanyIdAndEmployeeId(FIRST_COMPANY_ID, FIRST_EMPLOYEE_ID);

        assertTrue(Optional.of(res).isPresent());

        PaymentEntity firstPaymentResultEntity = res.get(0);
        assertEquals(DepositType.GIFT.name(), firstPaymentResultEntity.getDepositType());
        assertEquals(firstAmount.setScale(2, RoundingMode.FLOOR), firstPaymentResultEntity.getAmount());
        assertEquals(FIRST_COMPANY_ID, firstPaymentResultEntity.getCompanyEntity().getId());
        assertEquals(FIRST_EMPLOYEE_ID, firstPaymentResultEntity.getEmployeeEntity().getId());
        Calendar firstPaymentDate = toCalendar(firstPaymentResultEntity.getCreatedAt());
        assertEquals(currentYear, firstPaymentDate.get(Calendar.YEAR));
        // Calendar months start indexing from 0
        assertEquals(currentMonth,firstPaymentDate.get(Calendar.MONTH) + 1);
        assertEquals(currentDay,firstPaymentDate.get(Calendar.DAY_OF_MONTH));

        PaymentEntity secondPaymentResultEntity = res.get(1);
        assertEquals(DepositType.MEAL.name(), secondPaymentResultEntity.getDepositType());
        assertEquals(secondAmount.setScale(2, RoundingMode.FLOOR), secondPaymentResultEntity.getAmount());
        assertEquals(FIRST_COMPANY_ID, secondPaymentResultEntity.getCompanyEntity().getId());
        assertEquals(FIRST_EMPLOYEE_ID, secondPaymentResultEntity.getEmployeeEntity().getId());
        Calendar secondPaymentDate = toCalendar(secondPaymentResultEntity.getCreatedAt());
        assertEquals(currentYear, secondPaymentDate.get(Calendar.YEAR));
        // Calendar months start indexing from 0
        assertEquals(currentMonth,secondPaymentDate.get(Calendar.MONTH) + 1);
        assertEquals(currentDay,secondPaymentDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void throwsExceptionIfEmployeeHasNoCompany() {
        EmployeeEntity employeeWithoutCompany = buildEmployeeEntity(FIRST_EMPLOYEE_ID, FIRST_COMPANY_ID);
        employeeWithoutCompany.setCompanyEntity(null);
        assertThrows(EmployeeHasNoCompanyException.class, () -> commonService.checkCompanyAuthorizationOnEmployee(employeeWithoutCompany, FIRST_COMPANY_ID));
    }

    @Test
    public void throwsExceptionIfEmployeesCompanyDoesNotMatchGivenCompany() {
        EmployeeEntity employeeUnknownCompany = buildEmployeeEntity(FIRST_EMPLOYEE_ID, SECOND_COMPANY_ID);
        assertThrows(CompanyPaymentAuthorizationException.class, () -> commonService.checkCompanyAuthorizationOnEmployee(employeeUnknownCompany, FIRST_COMPANY_ID));
    }
    private EmployeeEntity buildEmployeeEntity(Long employeeId, Long companyId) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);
        CompanyEntity companyEntity = buildCompanyEntity(companyId);
        employeeEntity.setCompanyEntity(companyEntity);
        return employeeEntity;
    }

    private CompanyEntity buildCompanyEntity(Long companyId) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setBalance(new BigDecimal(1000));
        companyEntity.setId(companyId);
        return companyEntity;
    }

    private PaymentEntity buildPaymentEntity(EmployeeEntity employeeEntity, CompanyEntity companyEntity, DepositType depositType, BigDecimal amount) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setEmployeeEntity(employeeEntity);
        paymentEntity.setCompanyEntity(companyEntity);
        paymentEntity.setAmount(amount);
        paymentEntity.setDepositType(depositType.name());
        return paymentEntity;
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}