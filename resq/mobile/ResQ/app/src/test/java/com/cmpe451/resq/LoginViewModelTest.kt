package com.cmpe451.resq

import androidx.navigation.NavController
import com.cmpe451.resq.viewmodels.LoginViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private val navController = mockk<NavController>(relaxUnitFun = true)
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = LoginViewModel()
    }

    @Test
    fun `validateLoginInputs returns false if email is empty`() {
        val result = viewModel.validateLoginInputs("", "test-password")
        assertFalse(result)
        assertEquals("Email cannot be empty.", viewModel.errorMessage.value)
    }

    @Test
    fun `validateLoginInputs returns false if password is empty`() {
        val result = viewModel.validateLoginInputs("test@gmail.com", "")
        assertFalse(result)
        assertEquals("Password cannot be empty.", viewModel.errorMessage.value)
    }
}
