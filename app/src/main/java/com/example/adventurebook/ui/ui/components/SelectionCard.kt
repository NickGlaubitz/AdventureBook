package com.example.adventurebook.ui.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adventurebook.R

@Composable
fun SelectionCard(
    onSelect: (String) -> Unit,
    standardValue: String,
    title: String,
    options: List<String>,
    imageId: Int
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(standardValue) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Color(0xFF6650a4), shape = RoundedCornerShape(12.dp))
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.DarkGray)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("$title:", color = Color.LightGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("$selectedOption", color = Color.LightGray)
                }

                Surface(
                    modifier = Modifier
                        .size(80.dp, 80.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Transparent
                ) {
                    Image(
                        painterResource(imageId),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    tint = Color.LightGray
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Row(modifier = Modifier.padding(16.dp)) {
                    options.forEach { option ->
                        Card(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    selectedOption = option
                                    onSelect(selectedOption)
                                    isExpanded = false
                                },
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(Color.DarkGray)
                        ) {
                            Text(option, modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectionCardPrev() {
    SelectionCard(
        //onClick = TODO(),
        onSelect = {},
        //expanded = TODO(),
        title = "Wähle die Geschichtenart",
        options = listOf("Märchen", "Abenteuer", "Fantasie"),
        standardValue = "Abenteuer",
        imageId = R.drawable.world
    )
}