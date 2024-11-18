// ValidationUtilsTest.kt in `test` directory
package com.innotech.healapplication

import org.junit.Assert.* // For assertions
import org.junit.Test     // For test annotations

class ValidationUtilsTest {

    @Test
    fun emailValidator_CorrectEmail_ReturnsTrue() {
        assertTrue(ValidationUtils.isValidEmail("valid@example.com"))
    }

    @Test
    fun emailValidator_InvalidEmail_ReturnsFalse() {
        assertFalse(ValidationUtils.isValidEmail("invalid-email"))
    }

    @Test
    fun passwordValidator_ValidPassword_ReturnsTrue() {
        assertTrue(ValidationUtils.isValidPassword("StrongPass123"))
    }

    @Test
    fun passwordValidator_InvalidPassword_ReturnsFalse() {
        assertFalse(ValidationUtils.isValidPassword("123"))
    }
}
