package com.example.adventurebook.ui.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.adventurebook.data.local.Avatar
import com.example.adventurebook.data.viewmodel.OnboardingViewModel
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.example.adventurebook.ui.ui.components.ThemeSheet
import com.example.adventurebook.ui.ui.components.TypeSheet
import com.example.adventurebook.ui.ui.components.WorldSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, storyViewModel: StoryViewModel, onboardingViewModel: OnboardingViewModel) {

    var avatar by remember { mutableStateOf<Avatar?>(null) }
    var type by remember { mutableStateOf("Abenteuer") }
    var theme by remember { mutableStateOf("Freundschaft") }
    var world by remember { mutableStateOf("Zauberwald") }
    var characters by remember { mutableStateOf("Drache, Fee") }
    val scope = rememberCoroutineScope()
    val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
    var sheetContent by remember { mutableStateOf<@Composable () -> Unit>({}) }

    LaunchedEffect(Unit) {
        avatar = onboardingViewModel.getAvatar()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Willkommen ${avatar?.name ?: ""}") },
                actions = {
                    IconButton(onClick = { /* Einstellungen */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menü")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Neue Geschichte", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Typ Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            sheetContent = {
                                TypeSheet { selectedType ->
                                    type = selectedType
                                    scope.launch { sheetState.hide() }
                                }
                            }
                            sheetState.show()
                        }
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text("Geschichtenart: $type", modifier = Modifier.padding(16.dp))
            }

            // Theme Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            sheetContent = {
                                ThemeSheet { selectedTheme ->
                                    theme = selectedTheme
                                    scope.launch { sheetState.hide() }
                                }
                            }
                            sheetState.show()
                        }
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text("Geschichtenthema: $theme", modifier = Modifier.padding(16.dp))
            }

            // World Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            sheetContent = {
                                WorldSheet { selectedWorld ->
                                    world = selectedWorld
                                    scope.launch { sheetState.hide() }
                                }
                            }
                            sheetState.show()
                        }
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text("Welt: $world", modifier = Modifier.padding(16.dp))
            }

            // Nebencharaktere
            TextField(
                value = characters,
                onValueChange = { characters = it },
                placeholder = { Text("Nebencharaktere") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val characterList = characters.split(".").map { it.trim() }
                storyViewModel.generateStory(type, theme, world, characterList)
                navController.navigate("story")
            }) {
                Text("Geschichte erzeugen")
            }
        }

        BottomSheetScaffold(
            sheetContent = { sheetContent() }
        ) { }
    }
}