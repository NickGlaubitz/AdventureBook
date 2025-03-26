package com.example.adventurebook.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adventurebook.data.local.Character
import com.example.adventurebook.data.repos.CharacterRepoInterface

class CharacterViewModel(private val characterRepo: CharacterRepoInterface): ViewModel() {
    suspend fun saveCharacter(name: String) {
        val newCharacter = Character(
            name = name
        )

        characterRepo.insertCharacter(newCharacter)
    }

    suspend fun getAllCharacters(): List<Character> = characterRepo.getAllCharacters()
}