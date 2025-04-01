package com.example.adventurebook.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {

    @Insert
    suspend fun insertStory(story: Story): Long

    @Query("SELECT * FROM story ORDER BY createdAt DESC")
    fun getAllStories(): Flow<List<Story>>

    @Query("SELECT * FROM story WHERE id = :id")
    fun getStoryById(id: Int): Flow<Story?>

    @Delete
    suspend fun deleteStory(story: Story)

    @Update
    suspend fun updateStory(story: Story)
}