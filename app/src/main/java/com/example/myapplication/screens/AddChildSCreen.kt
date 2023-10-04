package com.example.myapplication.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.model.POSTGroupsRequest
import com.example.myapplication.network.HDRetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddChildScreen(
    navController: NavController
){
    val viewModel: AddChildViewModel = viewModel() // Create an instance of AuthViewModel

    var phoneNumberValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier) {
        Text("등록할 자녀의 핸드폰 번호를 입력해주세요.")
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = phoneNumberValue,
            onValueChange = { newValue ->
                // 숫자만 입력받도록 제한
                if (newValue.length <= 11 && newValue.isDigitsOnly()) {
                    phoneNumberValue = newValue
                }
            },
            label = { Text("숫자만 입력해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFD6F2FF)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
            ),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.submit(phoneNumberValue)
                keyboardController?.hide()
                navController.navigate(BottomNavItem.Main.screenRoute)
            })
        )
    }

}

class AddChildViewModel @Inject constructor() : ViewModel() {
    private val tag = "AddChild"
    fun submit(phoneNumberValue: String) {
        viewModelScope.launch(Dispatchers.IO){
            val response = HDRetrofitBuilder.HDapiService().postGroups(POSTGroupsRequest(phoneNumberValue))
            val buffer = response.body()
            if (buffer != null){
                Log.d(tag,buffer.data.id.toString())
            }
        }
    }
}