package com.example.adventurebook.ui.ui.screens

import android.text.TextUtils.replace
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.example.adventurebook.ui.ui.components.LoadingAnimation
import com.example.adventurebook.ui.ui.components.OptionCard
import com.example.adventurebook.ui.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryDetailScreen(
    navhController: NavController,
    viewModel: StoryViewModel,
    storyId: Int
) {
    val story by viewModel.getStoryById(storyId).collectAsState(initial = null)
    val options = story!!.options.split(";").filter { it.isNotBlank() }
    val context = LocalContext.current
    var currentParagraph by remember { mutableIntStateOf(0) }
    val isGenerating by viewModel.isGenerating.collectAsState()
    var contentHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    story?.let { it ->
        AsyncImage(
            model = it.ImageUrl,
            contentDescription = "Story Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(70.dp),
                    title = { /*Text(it.title, color = Color.White)*/ },
                    actions = {
                        IconButton(onClick = {
                            viewModel.deleteStory(it)
                            Toast.makeText(context, "Geschichte gelöscht", Toast.LENGTH_SHORT).show()
                            navhController.popBackStack()

                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Save", tint = Color.Red)
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
            val paragraphs = it.content
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
                        //.heightIn(max = 300.dp)
                        .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val heightPx = coordinates.size.height
                                contentHeight = with(density) { heightPx.toDp() } + 32.dp
                            }
                    ) {
                        if (currentParagraph < paragraphs.size) {
                            Text(
                                text = paragraphs.getOrNull(currentParagraph) ?: "Es ist ein Fehler aufgetreten, bitte erneut versuchen.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.9f),
                            )
                        } else if (options.isNotEmpty()) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (isGenerating) {
                                    LoadingAnimation()
                                } else {
                                    Text(
                                        text = "Wie soll deine Geschichte weitergehen?",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White.copy(0.9f),
                                    )

                                    options.forEach { option ->
                                        OptionCard(
                                            text = option,
                                            onClick = {
                                                viewModel.continueStory(option, story)
                                                currentParagraph = paragraphs.size
                                            }
                                        )
                                    }
                                }
                            }
                        }

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

                            Spacer(modifier = Modifier.width(40.dp))

                            IconButton(
                                onClick = { if (currentParagraph < paragraphs.size) currentParagraph++ },
                                enabled = currentParagraph < paragraphs.size
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