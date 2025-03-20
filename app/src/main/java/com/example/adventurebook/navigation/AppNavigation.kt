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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.example.adventurebook.ui.ui.screens.HomeScreen
import com.example.adventurebook.ui.ui.screens.LibraryScreen
import com.example.adventurebook.ui.ui.screens.StoryDetailScreen
import com.example.adventurebook.ui.ui.screens.StoryScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val storyViewModel: StoryViewModel = koinViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "Library") },
                    label = { Text("Library") },
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