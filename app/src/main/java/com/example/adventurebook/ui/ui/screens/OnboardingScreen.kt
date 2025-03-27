package com.example.adventurebook.ui.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adventurebook.MainActivity
import com.example.adventurebook.data.repos.AvatarRepoInterface
import com.example.adventurebook.data.viewmodel.OnboardingViewModel
import com.example.adventurebook.ui.ui.components.GenderCardBoy
import com.example.adventurebook.ui.ui.components.GenderCardGirl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingScreen(viewModel: OnboardingViewModel) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var ageRange by remember { mutableStateOf("") }
    var context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Avatar erstellen", color = Color.White, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                GenderCardGirl(onClick = { gender = "Mädchen"}, isSelected = gender.contains("Mädchen") )
                Spacer(modifier = Modifier.width(16.dp))
                GenderCardBoy(onClick = { gender = "Junge"}, isSelected = gender.contains("Junge"))
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Name eingeben") },
                label = { Text("Name") },
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Alter auswählen", color = Color.White, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { ageRange = "6-8" }
                        .border(width = if (ageRange == "6-8") 2.dp else 2.dp, color = if (ageRange == "6-8") Color.White else Color.Gray, shape = RoundedCornerShape(12.dp)),
                    elevation = CardDefaults.cardElevation( if (ageRange == "6-8") 8.dp else 2.dp),
                    colors = CardDefaults.cardColors(Color.DarkGray)
                ) {
                    Text("6-8", modifier = Modifier.padding(8.dp), color = Color.White)
                }

                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { ageRange = "8-10" }
                        .border(width = if (ageRange == "8-10") 2.dp else 2.dp, color = if (ageRange == "8-10") Color.White else Color.Gray, shape = RoundedCornerShape(12.dp)),
                    elevation = CardDefaults.cardElevation( if (ageRange == "8-10") 8.dp else 2.dp),
                    colors = CardDefaults.cardColors(Color.DarkGray)
                ) {
                    Text("8-10", modifier = Modifier.padding(8.dp), color = Color.White)
                }

                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { ageRange = "10-12" }
                        .border(width = if (ageRange == "10-12") 2.dp else 2.dp, color = if (ageRange == "10-12") Color.White else Color.Gray, shape = RoundedCornerShape(12.dp)),
                    elevation = CardDefaults.cardElevation( if (ageRange == "10-12") 8.dp else 2.dp),
                    colors = CardDefaults.cardColors(Color.DarkGray)
                ) {
                    Text("10-12", modifier = Modifier.padding(8.dp), color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.saveAvatar(name, gender, ageRange)
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as Activity).finish()
                    }
                }) {
                Text("Speichern")
            }
        }
    }
}
