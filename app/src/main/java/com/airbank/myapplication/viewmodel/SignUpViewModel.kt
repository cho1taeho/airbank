package com.airbank.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import com.airbank.myapplication.model.PATCHMembersRequest
import com.airbank.myapplication.repository.SignUpRepository
import com.airbank.myapplication.screens.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val TAG = "SIGNUP"
    private val repository = SignUpRepository(viewModelScope) // Create a repository for data operations
    // Function to perform user sign-up

    fun signUpUser(
        PATCHMembersRequest: PATCHMembersRequest,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // Call the signUp function from SignUpRepository on IO dispatcher
            repository.signUp(PATCHMembersRequest){
                Log.d(TAG,it.toString())
            }
        }
        navController.navigate(BottomNavItem.Main.screenRoute)
    }
}