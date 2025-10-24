package com.example.order.validator

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory;

// to validate mannualy jakarta bean validations
// I like to use this validator on the domain level when multiple field errors is needed
object InputValidator {

    val VALIDATION_FACTORY: ValidatorFactory = Validation.buildDefaultValidatorFactory()

    fun <T> valid(input: T?) {
        val violations: MutableSet<ConstraintViolation<T?>?> = VALIDATION_FACTORY.validator.validate(input)
        if (!violations.isEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }
}