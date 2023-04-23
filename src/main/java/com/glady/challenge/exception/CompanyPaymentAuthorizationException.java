package com.glady.challenge.exception;

import com.glady.challenge.exception.common.ErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CompanyPaymentAuthorizationException extends RuntimeException {
    public CompanyPaymentAuthorizationException(Long companyId) {
        super(String.format(ErrorEnum.PAYMENT_NOT_AUTHORIZED.getMessage(), companyId));
    }
}
