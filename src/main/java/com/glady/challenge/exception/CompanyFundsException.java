package com.glady.challenge.exception;

import com.glady.challenge.exception.common.ErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CompanyFundsException extends RuntimeException {
    public CompanyFundsException(BigDecimal companyBalance) {
        super(String.format(ErrorEnum.INSUFFICIENT_FUNDS.getMessage(), companyBalance));

    }
}
