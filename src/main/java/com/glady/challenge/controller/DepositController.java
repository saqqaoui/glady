package com.glady.challenge.controller;

import com.glady.challenge.business.DepositService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/deposit")
public class DepositController {
	private final DepositService depositService;
	public DepositController(DepositService depositService) {
		this.depositService = depositService;
	}

	@PutMapping("/gift")
	public void depositGift(@RequestParam Long employeeId, @RequestParam BigDecimal amount,
							@RequestHeader Long companyId) {
		depositService.depositGift(employeeId, amount, companyId);
	}

	@PutMapping("/meal")
	public void depositMeal(@RequestParam Long employeeId, @RequestParam BigDecimal amount,
							@RequestHeader Long companyId) {
		depositService.depositMeal(employeeId, amount, companyId);
	}

}
