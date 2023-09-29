package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.model.SignUpRequest
import com.example.myapplication.repository.SignUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(private val signUpRepository: SignUpRepository) : ViewModel() {
    fun signUpUser(
        requestDTO: SignUpRequest,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    signUpRepository.signUp(requestDTO)
                }
                result.onSuccess {
                    // Sign-up successful
                    onSuccess()
                }
                result.onFailure { error ->
                    // Handle the failure (e.g., show an error message)
                    onError()
                }
            } catch (e: Exception) {
                // Handle other exceptions (e.g., network issues)
                onError()
            }
        }
    }
}
