package com.glady.challenge.business;

import com.glady.challenge.business.model.DepositType;
import com.glady.challenge.business.service.IEmployeeService;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmployeeService.class})
public class EmployeeEntityServiceTest {

    private static GregorianCalendar dateInTheFuture;
    private static GregorianCalendar dateInThePast;
    public static final long COMPANY_ID = 99L;
    private IEmployeeService employeeService;
    @MockBean
    private CommonService commonService;

    @Before
    public void init() {

        dateInTheFuture = new GregorianCalendar();
        dateInTheFuture.add(Calendar.DAY_OF_YEAR, 1);
        dateInThePast = new GregorianCalendar(2014, Calendar.FEBRUARY, 11);
        
        this.employeeService = new EmployeeService(commonService);
    }
    @Test
    public void getEmployeeBalance_ExpiredMealTest() {
        PaymentEntity paymentEntity1 = buildDepositPayment(dateInThePast, DepositType.MEAL);
        EmployeeEntity employeeEntity = buildEmployee();
        when(commonService.findEntity(eq(EmployeeEntity.class), anyLong())).thenReturn(employeeEntity);
        when(commonService.getPaymentsByCompanyIdAndEmployeeId(anyLong(), anyLong())).thenReturn(List.of(paymentEntity1));
        BigDecimal employeeBalance = this.employeeService.calculateEmployeeBalance(1L);
        assertThat(employeeBalance).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void getEmployeeBalance_NotExpiredMealTest() {
        PaymentEntity paymentEntity1 = buildDepositPayment(dateInTheFuture, DepositType.MEAL);
        EmployeeEntity employeeEntity = buildEmployee();
        when(commonService.findEntity(eq(EmployeeEntity.class), anyLong())).thenReturn(employeeEntity);
        when(commonService.getPaymentsByCompanyIdAndEmployeeId(anyLong(), anyLong())).thenReturn(List.of(paymentEntity1));
        BigDecimal employeeBalance = this.employeeService.calculateEmployeeBalance(1L);
        BigDecimal actualBalance = BigDecimal.TEN.setScale(2, RoundingMode.FLOOR);
        assertThat(employeeBalance).isEqualTo(actualBalance);
    }
    @Test
    public void getEmployeeAccountBalance_ExpiredGiftTest() {
        PaymentEntity paymentEntity1 = buildDepositPayment(dateInThePast, DepositType.GIFT);
        EmployeeEntity employeeEntity = buildEmployee();
        when(commonService.findEntity(eq(EmployeeEntity.class), anyLong())).thenReturn(employeeEntity);
        when(commonService.getPaymentsByCompanyIdAndEmployeeId(anyLong(), anyLong())).thenReturn(List.of(paymentEntity1));
        BigDecimal employeeAccountBalance = this.employeeService.calculateEmployeeBalance(1L);
        assertThat(employeeAccountBalance).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void getEmployeeBalance_NotExpiredGiftTest() {
        PaymentEntity paymentEntity1 = buildDepositPayment(dateInTheFuture, DepositType.GIFT);
        EmployeeEntity employeeEntity = buildEmployee();
        when(commonService.findEntity(eq(EmployeeEntity.class), anyLong())).thenReturn(employeeEntity);
        when(commonService.getPaymentsByCompanyIdAndEmployeeId(anyLong(), anyLong())).thenReturn(List.of(paymentEntity1));
        BigDecimal employeeBalance = this.employeeService.calculateEmployeeBalance(1L);
        BigDecimal actualBalance = BigDecimal.TEN.setScale(2, RoundingMode.FLOOR);
        assertThat(employeeBalance).isEqualTo(actualBalance);
    }

    @Test
    public void getEmployeeBalance_ManyDepositTypesTest() {
        PaymentEntity paymentEntity1 = buildDepositPayment(dateInTheFuture, DepositType.GIFT);
        PaymentEntity paymentEntity2 = buildDepositPayment(dateInTheFuture, DepositType.MEAL);
        EmployeeEntity employeeEntity = buildEmployee();
        when(commonService.findEntity(eq(EmployeeEntity.class), anyLong())).thenReturn(employeeEntity);
        when(commonService.getPaymentsByCompanyIdAndEmployeeId(anyLong(), anyLong())).thenReturn(List.of(paymentEntity1, paymentEntity2));

        BigDecimal employeeBalance = this.employeeService.calculateEmployeeBalance(1L);
        BigDecimal actualBalance = BigDecimal.valueOf(20).setScale(2, RoundingMode.FLOOR);
        assertThat(employeeBalance).isEqualTo(actualBalance);
    }

    private EmployeeEntity buildEmployee() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setBalance(new BigDecimal(10000));
        companyEntity.setId(COMPANY_ID);
        employeeEntity.setCompanyEntity(companyEntity);
        return employeeEntity;
    }

    private static PaymentEntity buildDepositPayment(GregorianCalendar gregorianCalendar, DepositType depositType) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCompanyEntity(new CompanyEntity());
        paymentEntity.setDepositType(depositType.name());
        paymentEntity.setCreatedAt(gregorianCalendar.getTime());
        paymentEntity.setAmount(new BigDecimal(10));
        return paymentEntity;
    }
}