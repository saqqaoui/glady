package com.glady.challenge.business;

import com.glady.challenge.business.model.DepositType;
import com.glady.challenge.business.service.IDepositService;
import com.glady.challenge.exception.AmountException;
import com.glady.challenge.exception.CompanyFundsException;
import com.glady.challenge.repository.entity.CompanyEntity;
import com.glady.challenge.repository.entity.EmployeeEntity;
import com.glady.challenge.repository.entity.PaymentEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
public class DepositService implements IDepositService {

	private CommonService commonService;

	public DepositService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public void depositGift(Long employeeId, BigDecimal amount, Long requestCompanyId) {
		deposit(employeeId, amount, requestCompanyId, DepositType.GIFT);
	}
	@Override
	public void depositMeal(Long employeeId, BigDecimal amount, Long requestCompanyId) {
		deposit(employeeId, amount, requestCompanyId, DepositType.MEAL);
	}

	private void deposit(Long employeeId, BigDecimal amount, Long requestCompanyId, DepositType meal) {
		EmployeeEntity employeeEntity = (EmployeeEntity) commonService.findEntity(EmployeeEntity.class, employeeId);
		check(amount, employeeEntity, requestCompanyId);
		deposit(employeeEntity, amount, meal);
	}

	private void check(BigDecimal amount, EmployeeEntity employeeEntity, Long requestCompanyId) {
		commonService.findEntity(CompanyEntity.class, requestCompanyId);
		commonService.checkCompanyAuthorizationOnEmployee(employeeEntity, requestCompanyId);
		checkAmount(amount);
		checkFunds(amount, employeeEntity.getCompanyEntity().getBalance());
	}

	private static void checkAmount(BigDecimal amount) {
		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new AmountException();
		}
	}

	private static void checkFunds(BigDecimal amount, BigDecimal companyBalance) {
		if(companyBalance.compareTo(amount) < 0) {
			throw new CompanyFundsException(companyBalance);
		}
	}

	private void deposit(EmployeeEntity employeeEntity, BigDecimal amount, DepositType depositType) {
		employeeEntity.getCompanyEntity().setBalance(employeeEntity.getCompanyEntity().getBalance().subtract(amount));
		PaymentEntity paymentEntity = createPayment(employeeEntity, amount, depositType);
		save(employeeEntity, paymentEntity);
	}

	private static PaymentEntity createPayment(EmployeeEntity employeeEntity, BigDecimal amount, DepositType depositType) {
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setDepositType(depositType.name());
		paymentEntity.setAmount(amount);
		paymentEntity.setCompanyEntity(employeeEntity.getCompanyEntity());
		paymentEntity.setEmployeeEntity(employeeEntity);
		return paymentEntity;
	}
	private void save(EmployeeEntity employeeEntity, PaymentEntity paymentEntity) {
		commonService.createOrUpdate(employeeEntity, paymentEntity);
	}
}
