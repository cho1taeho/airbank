package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.kakao.sdk.user.UserApiClient


@Composable
fun MainScreen(navController: NavController) {
    Column {
        Text("관리중인 자녀 4부모", style = TextStyle(fontFamily = FontFamily(Font(R.font.pretendardregular))) )
        ChildProfile()
        Body(navController = navController)
    }
}

@Composable
fun ChildProfile() {
    var mainImage by remember { mutableIntStateOf(R.drawable.karina) }
    var mainName by remember { mutableStateOf("카리나") }

    var image1 by remember { mutableIntStateOf(R.drawable.karina2) }
    var name1 by remember { mutableStateOf("윈터") }

    var image2 by remember { mutableIntStateOf(R.drawable.karina3) }
    var name2 by remember { mutableStateOf("지젤") }

    var image3 by remember { mutableIntStateOf(R.drawable.karina4) }
    var name3 by remember { mutableStateOf("닝닝") }

    fun swapImages(imageRes: Int, newName: String){
        val tempImage = mainImage
        val tempName = mainName

        mainImage = imageRes
        mainName = newName

        when (imageRes) {
            image1 -> {
                image1 = tempImage
                name1 = tempName
            }

            image2 -> {
                image2 = tempImage
                name2 = tempName
            }

            image3 -> {
                image3 = tempImage
                name3 = tempName
            }

        }
    }

    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)

    ){
        Column (
            horizontalAlignment  = Alignment.Start,
//                modifier = Modifier.weight(1f)
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .border(1.dp, Color(0xFFB4EBF7), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = mainImage),
                    contentDescription = "Main Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
            Text(mainName)
        }
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.weight(1f)
        ){
            CircleWithImageAndBorder(image1, name1) {swapImages(image1, name1)}
            Spacer(Modifier.width(8.dp) )
            CircleWithImageAndBorder(image2, name2) {swapImages(image2, name2)}
            Spacer(Modifier.width(8.dp) )
            CircleWithImageAndBorder(image3, name3) {swapImages(image3, name3)}

        }
    }
    Spacer(modifier = Modifier.size(20.dp))

    ChildCard(mainImage = mainImage, mainName = mainName)
}
@Composable
fun CircleWithImageAndBorder(imageRes: Int, name: String, onClick: () -> Unit) {
    Column (
        horizontalAlignment  = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
                .clickable(onClick = onClick)
        ){
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
        Text(name)
    }
}
@Composable
fun ChildCard(mainImage: Int, mainName: String) {
    var imagepath by remember { mutableStateOf("")}
    UserApiClient.instance.me { user, _ ->
        if (user!=null){
            imagepath = user.properties?.get("profile_image") ?: ""
        }
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF5FCFEF))
    ){
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(imagepath.isEmpty()){
                    Image(
                        painter = painterResource(id = mainImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }else{
                    AsyncImage(
                        model = imagepath,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Text("자녀 $mainName 님의\n지갑을 관리하고 있습니다.")
            }
            Text("신용점수")
            ScoreBar(512)
        }
    }
    Spacer(modifier = Modifier.size(20.dp))
}
@Composable
fun ScoreBar(score: Int) {
    val maxScore = 1000
    val scoreWidth = (score.toFloat() / maxScore.toFloat())

    Row (
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
//                .padding(horizontal = 16.dp)
    ){
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(1f)
                .height(15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(scoreWidth)
                    .height(15.dp)
                    .background(Color.Green)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        Spacer( modifier = Modifier.size(10.dp) )

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Green,fontSize = 22.sp ,fontWeight = FontWeight.Bold)) {
                    append("$score")
                }
                append("/1000 P")
            }
        )
    }
}

@Composable
fun Body(navController: NavController) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(300.dp)
    ){
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xFFFFE9E8))
                .clickable {
                    navController.navigate("wallet")
                }
        ){
            Column (
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)

            ){
                Text(
                    "용돈\n사용 현황",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "이번 달에는\n얼마를 썼을까?",
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                )

            }
            Image(
                painter = painterResource(id = R.drawable.wallet),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.BottomEnd)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFE2ECFF))
                    .clickable {
                        navController.navigate("loan")
                    }
            ){
                Column (
                    modifier = Modifier
                        .padding(top=10.dp, start = 10.dp)
                ){
                    Text(
                        "땡겨쓰기\n현황",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "빌려간 돈\n확인하기",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )

                }

                Image(
                    painter = painterResource(id = R.drawable.loan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .offset(x = 18.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFEFE4FF))
                    .clickable {
                        navController.navigate("savings")
                    }
            ){
                Column (
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                ){
                    Text(
                        "티끌 모으기\n현황",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "저금 현황\n확인하기",
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.savings),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
}


