package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.navDeepLink
import kotlinx.coroutines.Dispatchers
import com.example.myapplication.model.SignUpRequest
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.repository.SignUpRepository
import com.example.myapplication.screens.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val TAG = "SIGNUP"
    private val repository = SignUpRepository(viewModelScope) // Create a repository for data operations
    // Function to perform user sign-up

    fun signUpUser(
        signUpRequest: SignUpRequest,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // Call the signUp function from SignUpRepository on IO dispatcher
            repository.signUp(signUpRequest){
                Log.d(TAG,it.toString())
            }
        }
        navController.navigate(BottomNavItem.Main.screenRoute)
    }
}