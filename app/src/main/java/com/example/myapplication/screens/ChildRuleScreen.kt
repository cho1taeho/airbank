package com.example.myapplication.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.AirbankApplication
import com.example.myapplication.model.POSTGroupsFundRequest
import com.example.myapplication.network.HDRetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun ChildRuleScreen( navController: NavController
) {
    val viewModel: ChildRuleViewModel = viewModel()

    var taxRate by remember { mutableStateOf("") }
    var allowanceAmount by remember { mutableStateOf("") }
    var allowanceDate by remember { mutableStateOf("") }
    var confiscationRate by remember { mutableStateOf("") }
    var loanLimit by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp)
    ) {
        Row() {
            OutlinedTextField(
                value = allowanceAmount,
                onValueChange = { allowanceAmount = it },
                label = { Text(text = "용돈") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f)
            )
        }
        Row() {
            OutlinedTextField(
                value = allowanceDate,
                onValueChange = { allowanceDate = it },
                label = { Text(text = "용돈 지급일") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
            Spacer(modifier = Modifier.width(20.dp))
            OutlinedTextField(
                value = taxRate,
                onValueChange = { taxRate = it },
                label = { Text(text = "세율") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
        }
        Row() {
            OutlinedTextField(
                value = loanLimit,
                onValueChange = { loanLimit = it },
                label = { Text(text = "땡겨쓰기 한도") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
            Spacer(modifier = Modifier.width(20.dp))
            OutlinedTextField(
                value = confiscationRate,
                onValueChange = { confiscationRate = it },
                label = { Text(text = "압류") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
        }

        Button(
            onClick = {
                val fundRequest: POSTGroupsFundRequest = POSTGroupsFundRequest(
                    taxRate = taxRate.toInt(),
                    allowanceAmount = allowanceAmount.toInt(),
                    allowanceDate = allowanceDate.toInt(),
                    confiscationRate = confiscationRate.toInt(),
                    loanLimit = loanLimit.toInt()
                )
                val group_id = AirbankApplication.prefs.getString("group_id","").toInt()
                if (!fundRequest.equals(POSTGroupsFundRequest(0,0,0,0,0)) && group_id != 0){
                    viewModel.handlesubmit(navController,fundRequest,group_id)
                }
            },
            modifier = Modifier.padding(top = 16.dp)

        ) {
            Text("SUBMIT")
        }
    }
}

class ChildRuleViewModel @Inject constructor() : ViewModel(){
    fun handlesubmit(navController: NavController, fundRequest: POSTGroupsFundRequest, group_id: Int){
        val tag = "ChildRule"
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(tag,"fundrequest: "+fundRequest.toString())
            Log.d(tag,"group_id: "+group_id.toString())
            val response = HDRetrofitBuilder.HDapiService().postGroupsFund(fundRequest,group_id)
            val postresponsedata = response.body()
            if(postresponsedata == null){
                val response2 = HDRetrofitBuilder.HDapiService().patchGroupsFund(fundRequest,group_id)
                val patchresponsedata = response2.body()
                Log.d(tag,patchresponsedata.toString())
                if(patchresponsedata != null) { //PATCH
                    AirbankApplication.prefs.setString("allowanceAmount",patchresponsedata.data.allowanceAmount.toString())
                    AirbankApplication.prefs.setString("allowanceDate",patchresponsedata.data.allowanceDate.toString())
                    AirbankApplication.prefs.setString("taxRate",patchresponsedata.data.taxRate.toString())
                    AirbankApplication.prefs.setString("confiscationRate",patchresponsedata.data.confiscationRate.toString())
                    AirbankApplication.prefs.setString("loanLimit",patchresponsedata.data.loanLimit.toString())
                }else{
                    Log.e(tag,"post and patch both failed")
                }
            }
            else{//POST
                Log.d(tag,postresponsedata.toString())
            }
        }
        navController.navigate(BottomNavItem.MyPage.screenRoute)
    }
}
