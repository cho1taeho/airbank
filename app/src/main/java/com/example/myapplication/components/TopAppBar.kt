package com.example.myapplication.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.AppNavigation
import com.example.myapplication.screens.BottomNavItem


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
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Centered TopAppBar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            NavigationBar(
                containerColor = Color(0xFFB4EBF7),
                content = {
                    val items = listOf(
                        BottomNavItem.Main,
                        BottomNavItem.Wallet,
                        BottomNavItem.Loan,
                        BottomNavItem.Savings,
                        BottomNavItem.Mypage
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





