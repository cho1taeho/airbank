package com.example.myapplication.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.AppNavigation
import com.example.myapplication.screens.BottomNavItem
import com.example.myapplication.R


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MyUI(navController: NavHostController) {
    AppNavigation(navController)
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppMainContent() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var title by remember {mutableStateOf("")}
    LaunchedEffect(navController){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = BottomNavItem.fromRoute(destination.route.toString()).title
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(BottomNavItem.main.screenRoute){
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {saveState = true}
                                }
                            }
                        }
                    ){
                    Image(
                        painterResource(id = R.drawable.airbank),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .fillMaxWidth() // Set the width to fill the available space
//                            .height(200.dp) // Set the desired height
//                            .width(200.dp)
                            .aspectRatio(1.5f) // Set the desired aspect ratio (width / height)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "notification"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFB4EBF7),
                content = {
                    val items = listOf(
                        BottomNavItem.main,
                        BottomNavItem.wallet,
                        BottomNavItem.loan,
                        BottomNavItem.savings,
                        BottomNavItem.mypage
                    )
                    items.forEach {item ->
                        NavigationBarItem(
                            icon = {Icon(painter = painterResource(id = item.icon), contentDescription = item.title)},
                            label = { Text(item.title) },
                            selected = currentRoute == item.screenRoute,
                            alwaysShowLabel = false,
                            onClick = {
                                navController.navigate(item.screenRoute){
                                    navController.graph.startDestinationRoute?.let {
                                        popUpTo(it) {saveState = true}
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
//                .fillMaxSize()
                .padding(it)
        ) {
            MyUI(navController =navController)
        }
    }
}





