package com.example.adventurebook.ui.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.adventurebook.MainActivity
import com.example.adventurebook.data.viewmodel.OnboardingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(viewModel: OnboardingViewModel) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profil erstellen")

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { gender = "Junge"} ) { Text("Junge") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { gender = "Mädchen"} ) { Text("Mädchen") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Name eingeben") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.saveAvatar(name, gender, "8")
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as Activity).finish()
            }
        }) {
            Text("Weiter")
        }
    }
}