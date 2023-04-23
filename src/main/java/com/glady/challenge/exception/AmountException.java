package com.glady.challenge.exception;

import com.glady.challenge.exception.common.ErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AmountException extends RuntimeException {
    public AmountException() {
        super(ErrorEnum.NEGATIVE_AMOUNT.getMessage());
    }
}
