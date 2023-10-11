package com.airbank.myapplication.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbank.myapplication.AirbankApplication
import com.airbank.myapplication.R
import com.airbank.myapplication.model.GETGroupsResponse
import com.airbank.myapplication.viewmodel.AccountViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WalletScreen(navController: NavController) {
    val accountViewModel: AccountViewModel = hiltViewModel()
    val accountData by accountViewModel.accountCheckState.collectAsState(initial = null)
    val mainName = com.airbank.myapplication.AirbankApplication.prefs.getString("name", "")
    val viewModel: MainViewModel = viewModel() // Create an instance of AuthViewModel
    var selectChild by remember { mutableStateOf(GETGroupsResponse.Data.Member(0,0,"","",0)) }
    selectChild = viewModel.selected ?: viewModel.childs.firstOrNull() ?: GETGroupsResponse.Data.Member(0,0,"","",0)

    val buffer = com.airbank.myapplication.AirbankApplication.prefs.getString("allowanceAmount","0").toInt()
    val decimal = DecimalFormat("#,###")
    val allowanceAmount = decimal.format(buffer)
    val allowanceDate = com.airbank.myapplication.AirbankApplication.prefs.getString("allowanceDate","0")

    var childs by remember { mutableStateOf<List<GETGroupsResponse.Data.Member>>(emptyList()) }
    LaunchedEffect(Unit, viewModel.childs){
        viewModel.getGroup()
        val mutablechilds = viewModel.childs
        childs = mutablechilds
    }
    LaunchedEffect(key1 = null ){
        accountViewModel.accountCheck()
    }
    
    val tamount = accountData?.data?.data?.amount ?:0

    val fommatTamount = NumberFormat.getNumberInstance(Locale.US).format(tamount)
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xffD6F2FF))
                .padding(horizontal = 16.dp)

        ){
            Column (
                modifier = Modifier
                    .padding(start = 13.dp)
            ){
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "잔액",
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    fommatTamount,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text("팡팡은행 $mainName 님의 통장")

            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
//                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xffD6F2FF))
                .clickable {
                    navController.navigate("BonusTransfer")
                }
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
            ){
                Spacer(modifier = Modifier.size(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.moneysend),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))

                Text("자녀에게 추가금액 보내기")
                
                Spacer(modifier = Modifier.weight(1f))
                
                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)      
                )
                
            }
        }
        Spacer(modifier = Modifier.size(17.dp))
        Divider(
            color = Color.Black,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.size(17.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .padding(horizontal = 16.dp)
                .clickable { navController.navigate("ChildRule") }

        ){
            Column (
                modifier = Modifier
                    .padding(start = 16.dp)
            ){
                Spacer(modifier = Modifier.size(10.dp))
                Text("${childs.firstOrNull()?.name ?: "Unknown"}님의 기본 용돈")
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "월 ${allowanceAmount}원",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text("매월 ${allowanceDate}일 자동이체")
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        val childname = childs.firstOrNull()?.name ?: "Unknown"
        CreditScoreBox(name = childname)
    }
}