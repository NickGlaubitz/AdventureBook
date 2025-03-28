package com.example.adventurebook.ui.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.adventurebook.data.viewmodel.StoryViewModel

@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: StoryViewModel
) {
    val stories by viewModel.allStories.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(stories) { story ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .border(width = 2.dp, color = Color(0xFF6650a4), shape = RoundedCornerShape(12.dp))

                    .clickable { navController.navigate("story/${story.id}") },
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.DarkGray)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

                    Surface(
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        AsyncImage(
                            model = story.ImageUrl,
                            contentDescription = "Story Image",
                            modifier = Modifier.size(70.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(story.title, style = MaterialTheme.typography.bodyLarge, color = Color.LightGray)
                    }
                }
            }
        }
    }
}