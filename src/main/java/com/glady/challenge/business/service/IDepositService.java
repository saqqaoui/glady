package com.glady.challenge.business.service;

import java.math.BigDecimal;

public interface IDepositService {
	void depositGift(Long employeeId, BigDecimal amount, Long requestCompanyId);

	void depositMeal(Long employeeId, BigDecimal amount, Long requestCompanyId);
}
