package com.example.adventurebook

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.adventurebook.data.viewmodel.OnboardingViewModel
import com.example.adventurebook.ui.ui.screens.OnBoardingScreen
import com.example.adventurebook.ui.ui.theme.AdventureBookTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class OnboardingActivity : ComponentActivity() {
    private val viewModel: OnboardingViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdventureBookTheme {
                OnBoardingScreen(viewModel)
            }
        }
        lifecycleScope.launch {
            if (viewModel.getAvatar() != null) {
                startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}

