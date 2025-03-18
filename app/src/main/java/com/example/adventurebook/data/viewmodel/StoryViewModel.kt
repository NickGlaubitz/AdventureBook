package com.example.adventurebook.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adventurebook.data.local.Story
import com.example.adventurebook.data.remote.OpenAiService
import com.example.adventurebook.data.repos.StoryRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepo: StoryRepoInterface, private val openAiService: OpenAiService): ViewModel() {
    
    val currentStory = MutableStateFlow<Story?>(null)
    
    fun generateStory(type: String, theme: String, world: String, supportingCharacters: List<String>) {
        viewModelScope.launch { 
            val prompt = "Erstelle eine $type Geschichte über $theme in einer $world mit den Nebencharakteren: ${supportingCharacters.joinToString(", ")}"
            
            val storyText = openAiService.generateText(prompt)
            
            val imageUrl = openAiService.generateImage(storyText)
            
            currentStory.value = Story(
                title = ,
                content = TODO(),
                ImageUrl = TODO()
            )
        }
    }
}