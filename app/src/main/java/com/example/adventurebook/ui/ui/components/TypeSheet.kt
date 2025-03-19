package com.example.adventurebook.ui.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TypeSheet(onSelect: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Wähle die Art der Geschichte", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(listOf("Abenteuer", "Märchen", "Fantasie")) { type ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onSelect(type) },
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(type, modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}