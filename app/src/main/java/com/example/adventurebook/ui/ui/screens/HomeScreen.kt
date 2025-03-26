package com.example.adventurebook.ui.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.adventurebook.data.local.Character
import com.example.adventurebook.data.repos.CharacterRepoInterface
import com.example.adventurebook.data.viewmodel.CharacterViewModel
import com.example.adventurebook.data.viewmodel.OnboardingViewModel
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.example.adventurebook.ui.ui.components.ThemeSheet
import com.example.adventurebook.ui.ui.components.TypeSheet
import com.example.adventurebook.ui.ui.components.WorldSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, storyViewModel: StoryViewModel) {

    val onboardingViewModel: OnboardingViewModel = koinViewModel()
    val characterViewModel: CharacterViewModel = koinViewModel()

    var avatar by remember { mutableStateOf<Avatar?>(null) }
    var characters by remember { mutableStateOf(listOf<Character>()) }
    var selectedCharacters by remember { mutableStateOf(listOf<String>()) }

    var type by remember { mutableStateOf("Abenteuer") }
    var theme by remember { mutableStateOf("Freundschaft") }
    var world by remember { mutableStateOf("Zauberwald") }

    var expandType by remember { mutableStateOf(false) }
    var expandTheme by remember { mutableStateOf(false) }
    var expandWorld by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        avatar = onboardingViewModel.getAvatar()
        characters = characterViewModel.getAllCharacters()
        avatar?.let { selectedCharacters = listOf(it.name) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Willkommen ${avatar?.name ?: ""}") },
                actions = {
                    IconButton(onClick = { /* Einstellungen */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menü")
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
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandType = !expandType }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Geschichtenart: $type")
                        Icon(
                            imageVector = if (expandType) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = ""
                        )
                    }
                    AnimatedVisibility(visible = expandType) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            listOf("Abenteuer", "Märchen", "Fantasie").forEach { option ->
                                Card(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .clickable {
                                            type = option
                                            expandType = false
                                        },
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Text(option, modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Theme Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandTheme = !expandTheme }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Geschichtentyp: $theme")
                        Icon(
                            imageVector = if (expandTheme) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = ""
                        )
                    }
                    AnimatedVisibility(visible = expandTheme) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            listOf("Freundschaft", "Mut", "Liebe").forEach { option ->
                                Card(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .clickable {
                                            theme = option
                                            expandTheme = false
                                        },
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Text(option, modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                    }
                }
            }

            // World Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandWorld = !expandWorld }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Geschichtenwelt: $world")
                        Icon(
                            imageVector = if (expandWorld) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = ""
                        )
                    }
                    AnimatedVisibility(visible = expandWorld) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            listOf("Weltraum", "Zauberwald", "Unterwasserwelt").forEach { option ->
                                Card(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .clickable {
                                            world = option
                                            expandWorld = false
                                        },
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Text(option, modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Characters
            Text("Nebencharaktere", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))



            Button(onClick = {
                val characterList = characters.split(".").map { it.trim() }
                storyViewModel.generateStory(type, theme, world, characterList)
                navController.navigate("story")
            }) {
                Text("Geschichte erzeugen")
            }
        }
    }

    if (showAddDialog) {
        BasicAlertDialog(
            onDismissRequest = { showAddDialog = false }
        ) {
            Surface(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Charakter hinzufügen", style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    var newCharacter by remember { mutableStateOf("") }
                    TextField(
                        value = newCharacter,
                        onValueChange = { newCharacter = it },
                        placeholder = { Text("Name") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                characterViewModel.saveCharacter(newCharacter)
                                characterViewModel.getAllCharacters()
                                showAddDialog = false
                            }
                        }
                    ) {
                        Text("Hinzufügen")
                    }
                }
            }
        }
    }
}