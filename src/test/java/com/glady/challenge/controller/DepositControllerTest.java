package com.glady.challenge.controller;

import com.glady.challenge.business.DepositService;
import com.glady.challenge.exception.*;
import com.glady.challenge.repository.entity.CompanyEntity;
import com.glady.challenge.repository.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {DepositController.class})
@AutoConfigureMockMvc
@AutoConfigureWebMvc
class DepositControllerTest {

	private static final Long EMPLOYEE_ID = 2L;
	private static final Long COMPANY_ID = 1L;
	private static final BigDecimal COMPANY_BALANCE = BigDecimal.valueOf(20);
	public static final String NOT_NUMBER = "not_number";
	@Autowired
	private MockMvc restDepositMockMvc;

	@MockBean
	private DepositService depositService;

	@Test
	void deposit_gift_NegativeAmount() throws Exception {
		doThrow(new AmountException()).when(depositService).depositGift(anyLong(), any(), anyLong());
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=-20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof AmountException))
				.andExpect(result -> assertEquals("You must enter an amount greater than or equal to zero", result.getResolvedException().getMessage()));
	}

	@Test
	void deposit_gift_CompanyNotFound() throws Exception {
		doThrow(new UnknownEntityException(CompanyEntity.class.getSimpleName(), COMPANY_ID)).when(depositService).depositGift(anyLong(), any(), anyLong());
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UnknownEntityException))
				.andExpect(result -> assertEquals("CompanyEntity with id 1 not found", result.getResolvedException().getMessage()));
	}

	@Test
	void deposit_gift_EmployeeHasNoCompany() throws Exception {
		doThrow(new EmployeeHasNoCompanyException(EMPLOYEE_ID)).when(depositService).depositGift(anyLong(), any(), anyLong());
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isForbidden())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeHasNoCompanyException))
				.andExpect(result -> assertEquals("The employeeEntity with id 2 does not have a known companyEntity", result.getResolvedException().getMessage()));
	}

	@Test
	void deposit_gift_EmployeeNotFound() throws Exception {
		doThrow(new UnknownEntityException(EmployeeEntity.class.getSimpleName(), EMPLOYEE_ID)).when(depositService).depositGift(anyLong(), any(), anyLong());
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UnknownEntityException))
				.andExpect(result -> assertEquals( "EmployeeEntity with id 2 not found", result.getResolvedException().getMessage()));
	}
	@Test
	void deposit_gift_InsufficientFunds() throws Exception {
		doThrow(new CompanyFundsException(COMPANY_BALANCE)).when(depositService).depositGift(anyLong(), any(), anyLong());
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isForbidden())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyFundsException))
				.andExpect(result -> assertEquals("You can not execute this operation because of insufficient funds, companyEntity balance is only 20", result.getResolvedException().getMessage()));
	}

	@Test
	void deposit_gift_CompanyNotAuthorizedToMakePayment() throws Exception {
		doThrow(new CompanyPaymentAuthorizationException(COMPANY_ID)).when(depositService).depositGift(anyLong(), any(), anyLong());
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isForbidden())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyPaymentAuthorizationException))
				.andExpect(result -> assertEquals("CompanyEntity with id 1 is not authorized to make operations for this employeeEntity", result.getResolvedException().getMessage()));
	}
	@Test
	void deposit_gift_MissingCompanyId() throws Exception {
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=-20"))
				.andExpectAll(
						status().isBadRequest(),
						status().reason(containsStringIgnoringCase("required")),
						status().reason(containsString("companyId"))
				);
	}
	@Test
	void deposit_gift_MissingParameterEmployeeId() throws Exception {
		this.restDepositMockMvc.perform(put("/deposit/gift?amount=20"))
				.andExpectAll(
						status().isBadRequest(),
						status().reason(containsStringIgnoringCase("required")),
						status().reason(containsString("employeeId"))
				);
	}
	@Test
	void deposit_gift_AmountIsNotNumber() throws Exception {
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=" + NOT_NUMBER)
						.header("companyId", COMPANY_ID))
				.andExpect(status().isBadRequest());
	}
	@Test
	void deposit_meal_AmountIsNotNumber() throws Exception {
		this.restDepositMockMvc.perform(put("/deposit/meal?employeeId="+EMPLOYEE_ID+"&amount=" + NOT_NUMBER)
						.header("companyId", COMPANY_ID))
				.andExpect(status().isBadRequest());
	}
	@Test
	void deposit_gift_MissingParameterAmount() throws Exception {
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID))
				.andExpectAll(
						status().isBadRequest(),
						status().reason(containsStringIgnoringCase("required")),
						status().reason(containsString("amount")));
	}
	@Test
	void deposit_gift_Ok() throws Exception {
		this.restDepositMockMvc.perform(put("/deposit/gift?employeeId="+EMPLOYEE_ID+"&amount=20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isOk());
	}
	@Test
	void deposit_meal_Ok() throws Exception {
		this.restDepositMockMvc.perform(put("/deposit/meal?employeeId="+EMPLOYEE_ID+"&amount=20")
						.header("companyId", COMPANY_ID))
				.andExpect(status().isOk());
	}
}
