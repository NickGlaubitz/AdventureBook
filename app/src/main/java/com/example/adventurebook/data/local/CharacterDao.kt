package com.example.adventurebook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Insert
    suspend fun  insertCharacter(character: Character)

    @Query("SELECT * FROM character")
    suspend fun getAllCharacters(): List<Character>
}