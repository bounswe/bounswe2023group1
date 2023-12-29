package com.cmpe451.resq

import com.cmpe451.resq.viewmodels.RegistrationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegistrationViewModelTest {

    private lateinit var viewModel: RegistrationViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = RegistrationViewModel()
    }

    @Test
    fun `validateRegistrationInputs returns false if name is empty`() {
        val result = viewModel.validateRegistrationInputs("", "Doe", "test@example.com", "password", "password", true)
        assertFalse(result)
        assertEquals("Name cannot be empty.", viewModel.errorMessage.value)
    }

    @Test
    fun `validateRegistrationInputs returns false if surname is empty`() {
        val result = viewModel.validateRegistrationInputs("John", "", "test@example.com", "password", "password", true)
        assertFalse(result)
        assertEquals("Surname cannot be empty.", viewModel.errorMessage.value)
    }

    @Test
    fun `validateRegistrationInputs returns false if email is empty`() {
        val result = viewModel.validateRegistrationInputs("John", "Doe", "", "password", "password", true)
        assertFalse(result)
        assertEquals("Email cannot be empty.", viewModel.errorMessage.value)
    }

    @Test
    fun `validateRegistrationInputs returns false if password is empty`() {
        val result = viewModel.validateRegistrationInputs("John", "Doe", "test@example.com", "", "password", true)
        assertFalse(result)
        assertEquals("Password cannot be empty.", viewModel.errorMessage.value)
    }

    @Test
    fun `validateRegistrationInputs returns false if password is less than 8 characters`() {
        val result = viewModel.validateRegistrationInputs("John", "Doe", "test@example.com", "pass", "pass", true)
        assertFalse(result)
        assertEquals("Password should be at least 8 characters long.", viewModel.errorMessage.value)
    }

    @Test
    fun `validateRegistrationInputs returns false if password and confirm password do not match`() {
        val result = viewModel.validateRegistrationInputs("John", "Doe", "test@example.com", "password123", "password", true)
        assertFalse(result)
        assertEquals("Passwords do not match!", viewModel.errorMessage.value)
    }

    @Test
    fun `validateRegistrationInputs returns false if terms are not accepted`() {
        val result = viewModel.validateRegistrationInputs("John", "Doe", "test@example.com", "password123", "password123", false)
        assertFalse(result)
        assertEquals("Please accept the terms and privacy policy.", viewModel.errorMessage.value)
    }
}
