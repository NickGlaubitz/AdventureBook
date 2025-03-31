package com.example.adventurebook.ui.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.adventurebook.R
import com.example.adventurebook.data.local.Avatar
import com.example.adventurebook.data.local.Character
import com.example.adventurebook.data.repos.CharacterRepoInterface
import com.example.adventurebook.data.viewmodel.CharacterViewModel
import com.example.adventurebook.data.viewmodel.OnboardingViewModel
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.example.adventurebook.ui.ui.components.CharacterButton
import com.example.adventurebook.ui.ui.components.SelectionCard
import com.example.adventurebook.ui.ui.theme.Background
import com.example.adventurebook.ui.ui.theme.Purple40
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
    var newCharState by remember { mutableStateOf("") }

    var type by remember { mutableStateOf("Abenteuer") }
    var theme by remember { mutableStateOf("Freundschaft") }
    var world by remember { mutableStateOf("Zauberwald") }

    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, newCharState) {
        avatar = onboardingViewModel.getAvatar()
        characters = characterViewModel.getAllCharacters()
        avatar?.let { selectedCharacters = listOf(it.name) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(70.dp),
                colors = TopAppBarColors(
                    containerColor = Background,
                    scrolledContainerColor = Color.DarkGray,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                title = { Text("Hallo ${avatar?.name ?: ""},") },
                actions = {
                    IconButton(onClick = { /* Einstellungen */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menü")
                    }
                }
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            //horizontalAlignment = Alignment.CenterHorizontally
            //verticalArrangement = Arrangement.Top
        ) {
            Text("lass uns eine neue Geschichte erstellen...", style = MaterialTheme.typography.bodyLarge, color = Color.White)

            Spacer(modifier = Modifier.height(32.dp))

            // Typ Card
            SelectionCard(
                onSelect = { type = it },
                title = "Wähle die Geschichtenart",
                options = listOf("Abenteuer", "Märchen", "Fantasie"),
                standardValue = "Abenteuer",
                imageId = R.drawable.ship
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Theme Card
            SelectionCard(
                onSelect = { theme = it },
                title = "Wähle das Geschichtenthema",
                options = listOf("Freundschaft", "Mut", "Liebe"),
                standardValue = "Freundschaft",
                imageId = R.drawable.book
            )

            Spacer(modifier = Modifier.height(8.dp))

            // World Card
            SelectionCard(
                onSelect = { world = it },
                title = "Wähle die Welt der Geschichte",
                options = listOf("Weltraum", "Zauberwald", "Unterwasserwelt"),
                standardValue = "Weltraum",
                imageId = R.drawable.world
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Characters
            Text("Füge Nebencharaktere hinzu:", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow {
                item {
                    avatar?.let {
                        CharacterButton(
                            name = it.name,
                            isSelected = selectedCharacters.contains(it.name),
                            onClick = {
                                selectedCharacters = if (selectedCharacters.contains(it.name)) {
                                    selectedCharacters - it.name
                                } else {
                                    selectedCharacters + it.name
                                }
                            }
                        )
                    }
                }
                items(characters) { character ->
                    CharacterButton(
                        name = character.name,
                        isSelected = selectedCharacters.contains(character.name),
                        onClick = {
                            selectedCharacters = if (selectedCharacters.contains(character.name)) {
                                selectedCharacters - character.name
                            } else {
                                selectedCharacters + character.name
                            }
                        }
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Purple40)
                            .clickable { showAddDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.LightGray)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = {
                    storyViewModel.generateStory(type, theme, world, selectedCharacters)
                    navController.navigate("story")
                }) {
                    Text("Los geht´s", color = Color.White)
                }
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
                                newCharState = newCharacter
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