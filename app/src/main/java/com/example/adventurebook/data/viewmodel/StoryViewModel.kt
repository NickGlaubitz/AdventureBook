package com.example.adventurebook.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adventurebook.data.local.Story
import com.example.adventurebook.data.remote.OpenAiService
import com.example.adventurebook.data.repos.AvatarRepoInterface
import com.example.adventurebook.data.repos.StoryRepoInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content

class StoryViewModel(private val storyRepo: StoryRepoInterface, private val openAiService: OpenAiService, private val avatarRepo: AvatarRepoInterface): ViewModel() {

    val currentStory = MutableStateFlow<Story?>(null)
    val continuationOptions = MutableStateFlow<List<String>>(emptyList())
    val isGenerating = MutableStateFlow(false)

    fun generateStory(type: String, theme: String, world: String, supportingCharacters: List<String>) {
        viewModelScope.launch {
            val avatar = avatarRepo.getAvatar() ?: return@launch

            val prompt = """
                Erstelle eine $type Geschichte über $theme in einer $world mit den Nebencharakteren: ${supportingCharacters.joinToString(", ")} für ein Kind namens ${avatar.name} im Alter von ${avatar.age}.
                Am Ende der Geschichte füge 2 kurze Fortsetzungsoptionen hinzu, wie sie weitergehen könnte, im Format:
                **Fortsetzungsoptionen**
                1. [Option 1]
                2. [Option 2]
            """.trimIndent()

            isGenerating.value = true

            val storyText = openAiService.generateText(prompt)

            // Titel trennen
            val titleMatch = Regex("\\*\\*(.*?)\\*\\*").find(storyText)
            var title = titleMatch?.groupValues?.get(1)?.replace(Regex("^Titel:\\s*"), "") ?: "Neue Geschichte für ${avatar.name}"
            //title = title.replace(Regex("^Titel:\\s*"), "")

            // Optionen trennen
            val optionsMatch = Regex("\\*\\*Fortsetzungsoptionen\\*\\*\\n([\\s\\S]*?)$", RegexOption.DOT_MATCHES_ALL).find(storyText)
            val optionsRaw = optionsMatch?.groupValues?.get(1)?.trim() ?: ""
            val options = optionsRaw.split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() && it.matches(Regex("^\\d\\.\\s*.*")) }
                .map { it.replaceFirst(Regex("^\\d\\.\\s*\\*\\*?"), "").replace(Regex("\\*\\*?$"), "").trim() }

            // Content trennen
            val content = storyText.substringBefore("**Fortsetzungsoptionen**")
                .replace(Regex("\\*\\*.*?\\*\\*\\n*"), "")
                .trim()

            val imageUrl = openAiService.generateImage(storyText.substring(0, minOf(100, storyText.length)))

            currentStory.value = Story(
                title = title,
                content = content,
                ImageUrl = imageUrl,
                options = options.joinToString(";")
            )

            continuationOptions.value = options
            Log.d("StoryViewModel", "Raw options: $optionsRaw")
            Log.d("StoryViewModel", "Extracted options: $options")

            isGenerating.value = false
        }
    }

    fun continueStory(selectedOption: String, story: Story? = currentStory.value) {
        viewModelScope.launch {
            val current = story ?: return@launch
            val avatar = avatarRepo.getAvatar() ?: return@launch

            val prompt = """
                Setze die folgende Geschichte fort, basierend auf der gewählten Option: $selectedOption.
                Hier ist der bisherige Inhalt: "${current.content}".
                Schreibe den nächsten Teil der Geschichte für ein Kind namens ${avatar.name} im Alter von ${avatar.age} und füge am Ende wieder 2 kurze Fortsetzungsoptionen hinzu im Format:
                **Fortsetzungsoptionen**
                1. [Option 1]
                2. [Option 2]
            """.trimIndent()

            isGenerating.value = true

            val continuationText = openAiService.generateText(prompt)

            // Optionen trennen
            val optionsMatch = Regex("\\*\\*Fortsetzungsoptionen\\*\\*\\n([\\s\\S]*?)$", RegexOption.DOT_MATCHES_ALL).find(continuationText)
            val optionsRaw = optionsMatch?.groupValues?.get(1)?.trim() ?: ""
            val newOptions = optionsRaw.split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() && it.matches(Regex("^\\d\\.\\s*.*")) }
                .map { it.replaceFirst(Regex("^\\d\\.\\s*\\*\\*?"), "").replace(Regex("\\*\\*?$"), "").trim() }

            // Content trennen
            val newContent = continuationText.substringBefore("**Fortsetzungsoptionen**")
                .replace(Regex("\\*\\*.*?\\*\\*\\n*"), "")
                .trim()

            val newImageUrl = openAiService.generateImage(newContent.substring(0, minOf(100, newContent.length)))

            val updatedStory = current.copy(
                content = "${current.content}\n\n$newContent",
                ImageUrl = newImageUrl,
                options = newOptions.joinToString(";")
            )

            if (current.id.toLong() == 0L) {
                currentStory.value = updatedStory
            } else {
                storyRepo.updateStory(updatedStory)
            }

            continuationOptions.value = newOptions

            isGenerating.value = false
        }
    }

    fun saveCurrentStory(onSaved: (Long) -> Unit) {
        currentStory.value?.let { story ->
            viewModelScope.launch {
                val updatedStory = story.copy(options = continuationOptions.value.joinToString(";"))
                val id = storyRepo.insertStory(updatedStory)
                currentStory.value = null
                continuationOptions.value = emptyList()
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