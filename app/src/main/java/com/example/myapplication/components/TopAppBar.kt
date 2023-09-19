package com.example.myapplication.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun SongsPage() {
    // Content of the Songs page
    Text(text = "Songs Page Content")
}

@Composable
fun ArtistsPage() {
    // Content of the Artists page
    Text(text = "Artists Page Content")
}

@Composable
fun PlaylistsPage() {
    // Content of the Playlists page
    Text(text = "Playlists Page Content")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyUI(navController: NavHostController) {
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
            BottomAppBar(
                containerColor = Color(0xFFB4EBF7),
                content = {
                    // Add your bottom bar content here
                    var selectedItem by remember { mutableIntStateOf(0) }
                    val items = listOf("Songs", "Artists", "Playlists")
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    if (selectedItem != index) {
                                        selectedItem = index
                                        val route = when (index) {
                                            0 -> "songs"
                                            1 -> "artists"
                                            2 -> "playlists"
                                            else -> "songs" // Default route
                                        }
                                        navController.navigate(route)
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
//        val currentBackStackEntry by rememberUpdatedState(navController.currentBackStackEntryAsState())
            val currentRoute = navController.currentBackStackEntry?.arguments?.getString("route")
            when (currentRoute) {
                "songs" -> SongsPage()
                "artists" -> ArtistsPage()
                "playlists" -> PlaylistsPage()
                else -> SongsPage() // Default page
            }
        }
    }
}



