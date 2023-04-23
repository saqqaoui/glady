package com.glady.challenge.exception.common;

/**
 * Enum for errors messages to report when REST exceptions are thrown
 */
public enum ErrorEnum {
    // Error enum value when companyEntity have not sufficient funds to make a payment for employees
    INSUFFICIENT_FUNDS("You can not execute this operation because of insufficient funds, companyEntity balance is only %s"),
    // Error enum value when the given amount is <= 0
    NEGATIVE_AMOUNT("You must enter an amount greater than or equal to zero"),
    // Error enum value when entity with given id does not exist in the database
    ENTITY_NOT_FOUND("%s with id %s not found"),
    // Error enum value when companyEntity is not the attached to the employeeEntity
    PAYMENT_NOT_AUTHORIZED("CompanyEntity with id %s is not authorized to make operations for this employeeEntity"),
    // Error enum value when employeeEntity has no companyEntity
    EMPLOYEE_HAS_NO_COMPANY("The employeeEntity with id %s does not have a known companyEntity");

    private final String message;

    ErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
