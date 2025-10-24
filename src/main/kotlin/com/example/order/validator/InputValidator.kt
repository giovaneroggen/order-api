package com.example.order.validator
//
//import jakarta.validation.ValidatorFactory;
//
//object InputValidator {
//
//    var VALIDATION_FACTORY: ValidatorFactory? = null
//
//    fun <T> valid(input: T?) {
//        VALIDATION_FACTORY = getValidationFactory()
//        val violations: MutableSet<ConstraintViolation<T?>?> = VALIDATION_FACTORY.getValidator().validate(input)
//        if (!violations.isEmpty()) {
//            throw ConstraintViolationException(violations)
//        }
//    }
//
//    private fun getValidationFactory(): ValidatorFactory? {
//        if (VALIDATION_FACTORY == null) VALIDATION_FACTORY = Validation.buildDefaultValidatorFactory()
//        return VALIDATION_FACTORY
//    }
//}