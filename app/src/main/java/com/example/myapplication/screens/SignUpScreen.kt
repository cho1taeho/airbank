package com.example.myapplication.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.model.SignUpRequest
import com.example.myapplication.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    var phoneNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val TAG = "SIGN UP"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            singleLine = true,
            placeholder = {
                Text(text = "Phone Number")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            placeholder = {
                Text(text = "Name")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Show a warning message if showError is true
        if (showError) {
            Text(
                text = "Please fill in all required fields",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Role Selection
        Column(Modifier.selectableGroup()) {
            RadioButton(selected = role == "Child", onClick = { role = "Child" })
            RadioButton(selected = role == "Parent", onClick = { role = "Parent" })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (phoneNumber.isNotEmpty() && name.isNotEmpty() && role.isNotEmpty()) {
                    val requestDTO = SignUpRequest(
                        name = name,
                        phoneNumber = phoneNumber,
                        role = role
                    )
                    // Call the ViewModel function to perform sign-up
                    viewModel.signUpUser(
                        requestDTO,
                        onSuccess = {
                            Log.d(TAG,"succeesseseseses")
                            navController.navigate(BottomNavItem.Main.screenRoute)
                        },
                        onError = {
                            Log.d(TAG,"noooooooooo")
                        })
                } else {
                    // Set showError to true to display the warning message
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Sign Up")
        }
    }
}
