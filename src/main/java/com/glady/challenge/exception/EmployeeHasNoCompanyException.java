package com.glady.challenge.exception;

import com.glady.challenge.exception.common.ErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EmployeeHasNoCompanyException extends RuntimeException {
    public EmployeeHasNoCompanyException(Long employeeId) {
        super(String.format(ErrorEnum.EMPLOYEE_HAS_NO_COMPANY.getMessage(), employeeId));
    }
}
