package com.glady.challenge.exception;

import com.glady.challenge.exception.common.ErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownEntityException extends RuntimeException {
    public UnknownEntityException(String entityName, Long entityId) {
        super(String.format(ErrorEnum.ENTITY_NOT_FOUND.getMessage(), entityName, entityId));

    }
}
