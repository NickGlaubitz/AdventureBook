package com.example.adventurebook.data.repos

import com.example.adventurebook.data.local.Character
import com.example.adventurebook.data.local.CharacterDao

interface CharacterRepoInterface {
    suspend fun insertCharacter(character: Character)
    suspend fun getAllCharacters(): List<Character>
}

class CharacterRepoImpl(private val dao: CharacterDao): CharacterRepoInterface {
    override suspend fun insertCharacter(character: Character) = dao.insertCharacter(character)
    override suspend fun getAllCharacters(): List<Character> = dao.getAllCharacters()
}