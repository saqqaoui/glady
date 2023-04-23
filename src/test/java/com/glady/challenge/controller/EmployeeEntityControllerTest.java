package com.glady.challenge.controller;

import com.glady.challenge.business.EmployeeService;
import com.glady.challenge.exception.EmployeeHasNoCompanyException;
import com.glady.challenge.exception.UnknownEntityException;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {EmployeeController.class})
@AutoConfigureMockMvc
@AutoConfigureWebMvc
class EmployeeEntityControllerTest {

	public static final int EMPLOYEE_BALANCE = 5;
	private static final Long EMPLOYEE_ID = 1L;
	private static final Long COMPANY_ID = 2L;
	public static final String NOT_NUMBER_PARAM = "NOT_NUMBER";
	public static final Long UNDEFINED_EMPLOYEE_ID = 9L;
	@Autowired
	private MockMvc restEmployeeMockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Test
	void getEmployeeBalance_EmployeeNotFound() throws Exception {
		doThrow(new UnknownEntityException("EmployeeEntity", UNDEFINED_EMPLOYEE_ID)).when(employeeService).calculateEmployeeBalance(anyLong());
		this.restEmployeeMockMvc.perform(get("/user/balance?employeeId=" + UNDEFINED_EMPLOYEE_ID))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UnknownEntityException))
				.andExpect(result -> assertEquals(result.getResolvedException().getMessage(), "EmployeeEntity with id "+UNDEFINED_EMPLOYEE_ID+" not found"));

	}
	@Test
	void getEmployeeBalance_CompanyNotFound() throws Exception {
		doThrow(new EmployeeHasNoCompanyException(UNDEFINED_EMPLOYEE_ID)).when(employeeService).calculateEmployeeBalance(any());
		this.restEmployeeMockMvc.perform(get("/user/balance?employeeId=" + UNDEFINED_EMPLOYEE_ID))
				.andExpect(status().isForbidden())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeHasNoCompanyException))
				.andExpect(result -> assertEquals(result.getResolvedException().getMessage(), "The employeeEntity with id "+ UNDEFINED_EMPLOYEE_ID +" does not have a known companyEntity"));
	}
	@Test
	void getEmployeeBalance_ParameterEmployeeIdIsNull() throws Exception {
		this.restEmployeeMockMvc.perform(get("/user/balance?employeeId="))
				.andExpectAll(
						status().isBadRequest(),
						status().reason(containsStringIgnoringCase("required")),
						status().reason(containsString("employeeId")));
	}
	@Test
	void getEmployeeBalance_MissingParameterEmployeeId() throws Exception {
		this.restEmployeeMockMvc.perform(get("/user/balance"))
				.andExpectAll(
						status().isBadRequest(),
						status().reason(containsStringIgnoringCase("required")),
						status().reason(containsString("employeeId")));
	}
	@Test
	void getEmployeeBalance_ParameterEmployeeIdIsNotANumber() throws Exception {
		this.restEmployeeMockMvc.perform(get("/user/balance?employeeId=" + NOT_NUMBER_PARAM))
				.andExpectAll(
						status().isBadRequest());
						status().reason(containsString("NumberFormatException"));
	}
	@Test
	void getEmployeeBalance_Ok() throws Exception {
		when(employeeService.calculateEmployeeBalance(anyLong())).thenReturn(new BigDecimal(EMPLOYEE_BALANCE));
		this.restEmployeeMockMvc.perform(get("/user/balance?employeeId=" + EMPLOYEE_ID)
						.header("companyId", COMPANY_ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value(EMPLOYEE_BALANCE));
	}
}
