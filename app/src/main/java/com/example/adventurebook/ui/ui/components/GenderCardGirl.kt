package com.example.adventurebook.ui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adventurebook.R

@Composable
fun GenderCardGirl(isSelected: Boolean, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .size(width = 130.dp, height = 180.dp)
            .border(if (isSelected) 2.dp else 2.dp, if (isSelected) Color.Magenta else Color.Gray, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = CardDefaults.outlinedShape,
        elevation = CardDefaults.cardElevation(if (isSelected) 6.dp else 0.dp),
        colors = CardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Mädchen")
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painterResource(R.drawable.femalesymbol),
                contentDescription = "Boy"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun GenderCardPrev() {
    GenderCardGirl(
        isSelected = false
    ) { }
}