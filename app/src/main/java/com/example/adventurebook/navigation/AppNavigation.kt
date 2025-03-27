package com.example.adventurebook.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.example.adventurebook.ui.ui.screens.HomeScreen
import com.example.adventurebook.ui.ui.screens.LibraryScreen
import com.example.adventurebook.ui.ui.screens.StoryDetailScreen
import com.example.adventurebook.ui.ui.screens.StoryScreen
import com.example.adventurebook.ui.ui.theme.Background
import com.example.adventurebook.ui.ui.theme.PurpleGrey40
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val storyViewModel: StoryViewModel = koinViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar(containerColor = Background, contentColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Black) },
                    label = { Text("Home", color = Color.LightGray) },
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "Library", tint = Color.Black) },
                    label = { Text("Library", color = Color.LightGray) },
                    selected = currentRoute == "library",
                    onClick = {
                        navController.navigate("library") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") { HomeScreen(navController, storyViewModel) }
            composable("story") { StoryScreen(navController, storyViewModel) }
            composable("library") { LibraryScreen(navController, storyViewModel) }
            composable("story/{storyId}") { backStackEntry ->
                val storyId = backStackEntry.arguments?.getString("storyId")?.toIntOrNull()
                if (storyId != null) {
                    StoryDetailScreen(navController, storyViewModel, storyId)
                }
            }
        }
    }
}