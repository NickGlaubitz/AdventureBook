package com.example.adventurebook.ui.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.adventurebook.data.viewmodel.StoryViewModel

@Composable
fun StoryDetailScreen(
    navhController: NavController,
    viewModel: StoryViewModel,
    storyId: Int
) {
    val story by viewModel.getStoryById(storyId).collectAsState(initial = null)
    val context = LocalContext.current

    story?.let {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = it.ImageUrl,
                contentDescription = "Story Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(
                        it.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Text(
                        it.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Row {
                        Button(onClick = {
                            viewModel.saveCurrentStory { id ->
                                Toast.makeText(
                                    context,
                                    "Geschichte gespeichert",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Text("Speichern")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(onClick = {
                            // Fortsetzen später integrieren
                            Toast.makeText(
                                context,
                                "Fortsetzung noh nicht implementiert",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Text("Fortsetzen")
                        }
                    }
                }
            }
        }
    }