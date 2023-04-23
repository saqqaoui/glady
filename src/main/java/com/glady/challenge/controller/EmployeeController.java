package com.glady.challenge.controller;

import com.glady.challenge.business.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/user")
public class EmployeeController {


	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/balance")
	public BigDecimal getEmployeeBalance(@RequestParam Long employeeId) {
		return employeeService.calculateEmployeeBalance(employeeId);
	}
}
