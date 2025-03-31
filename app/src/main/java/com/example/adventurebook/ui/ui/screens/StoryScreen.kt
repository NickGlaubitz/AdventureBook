package com.example.adventurebook.ui.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.example.adventurebook.ui.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(
    navController: NavController,
    viewModel: StoryViewModel
) {
    val story by viewModel.currentStory.collectAsState()
    val options by viewModel.continuationOptions.collectAsState()
    val context = LocalContext.current
    var currentParagraph by remember { mutableIntStateOf(0) }
    var isContinuing by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (story == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
            )
        } else {

            AsyncImage(
                model = story!!.ImageUrl,
                contentDescription = "Story Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.height(70.dp),
                        title = { /*Text(story!!.title, color = Color.White)*/ },
                        actions = {
                            IconButton(onClick = {
                                viewModel.saveCurrentStory { id ->
                                    Toast.makeText(context, "Geschichte gespeichert", Toast.LENGTH_SHORT).show()
                                }
                                navController.navigate("Library")
                            }) {
                                Icon(Icons.Default.Favorite, contentDescription = "Save")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            Color.Transparent,
                            scrolledContainerColor = Color.Transparent,
                            navigationIconContentColor = Color.Transparent,
                            titleContentColor = Color.White,
                            actionIconContentColor = Color.White
                        )
                    )
                },
                containerColor = Color.Transparent
            ) { padding ->
                val paragraphs = story!!.content
                    .replace(Regex("^\\*\\*.*?\\*\\*\\n*"), "")
                    .split("\n\n")
                    .filter { it.isNotBlank() }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),

                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = paragraphs.getOrNull(currentParagraph) ?: "Wie soll es weitergehen?",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState())
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = { if (currentParagraph > 0) currentParagraph-- },
                                    enabled = currentParagraph > 0
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(32.dp))
                                }

                                if (currentParagraph == paragraphs.size - 1) {
                                    if (isContinuing) {
                                        CircularProgressIndicator(modifier = Modifier.size(50.dp), color = Purple40)
                                    } else if (options.isNotEmpty()) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            options.forEach { option ->
                                                TextButton(
                                                    onClick = {
                                                        isContinuing = true
                                                        viewModel.continueStory(option)
                                                        isContinuing = false
                                                    }
                                                ) {
                                                    Text(option, color = Color.White.copy(0.9f))
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Spacer(modifier = Modifier.width(48.dp))
                                }

                                IconButton(
                                    onClick = { if (currentParagraph < paragraphs.size - 1) currentParagraph++ },
                                    enabled = currentParagraph < paragraphs.size - 1
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(32.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}