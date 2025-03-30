package com.example.adventurebook.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adventurebook.data.local.Story
import com.example.adventurebook.data.remote.OpenAiService
import com.example.adventurebook.data.repos.AvatarRepoInterface
import com.example.adventurebook.data.repos.StoryRepoInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepo: StoryRepoInterface, private val openAiService: OpenAiService, private val avatarRepo: AvatarRepoInterface): ViewModel() {
    
    val currentStory = MutableStateFlow<Story?>(null)
    
    fun generateStory(type: String, theme: String, world: String, supportingCharacters: List<String>) {
        viewModelScope.launch {
            val avatar = avatarRepo.getAvatar() ?: return@launch

            val prompt = "Erstelle eine $type Geschichte über $theme in einer $world mit den Nebencharakteren: ${supportingCharacters.joinToString(", ")} für ein Kind namens ${avatar.name} im Alter von ${avatar.age}"
            
            val storyText = openAiService.generateText(prompt)
            
            val imageUrl = openAiService.generateImage(storyText.substring(0, minOf(100, storyText.length)))

            // Titel und Inhalt trennen

            val titleMatch = Regex("\\*\\*(.*?)\\*\\*").find(storyText)
            var title = titleMatch?.groupValues?.get(1) ?: "Neue Geschichte für ${avatar.name}"
            title = title.replace(Regex("^Titel:\\s*"), "")
            val content = storyText.replace(Regex("\\*\\*.*?\\*\\*\\n*"), "").trim()
            
            currentStory.value = Story(
                title = title,
                content = content,
                ImageUrl = imageUrl
            )
        }
    }

    fun saveCurrentStory(onSaved: (Long) -> Unit) {
        currentStory.value?.let { story ->
            viewModelScope.launch {
                val id = storyRepo.insertStory(story)
                currentStory.value = null
                onSaved(id)
            }
        }
    }

    fun getStoryById(id: Int): Flow<Story?> = storyRepo.getStoryById(id)

    val allStories: Flow<List<Story>> = storyRepo.getAllStories()

    fun deleteStory(story: Story) {
        viewModelScope.launch {
            storyRepo.deleteStory(story)
        }
    }
}