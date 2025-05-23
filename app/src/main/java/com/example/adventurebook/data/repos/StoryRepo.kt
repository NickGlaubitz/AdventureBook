package com.example.adventurebook.data.repos

import com.example.adventurebook.data.local.Story
import com.example.adventurebook.data.local.StoryDao
import kotlinx.coroutines.flow.Flow

interface StoryRepoInterface {
    suspend fun insertStory(story: Story): Long
    fun getAllStories(): Flow<List<Story>>
    fun getStoryById(id: Int): Flow<Story?>
    suspend fun deleteStory(story: Story)
    suspend fun updateStory(story: Story)
}

class StoryRepoImpl(private val dao: StoryDao): StoryRepoInterface {
    override suspend fun insertStory(story: Story): Long = dao.insertStory(story)
    override fun getAllStories(): Flow<List<Story>> = dao.getAllStories()
    override fun getStoryById(id: Int): Flow<Story?> = dao.getStoryById(id)
    override suspend fun deleteStory(story: Story) = dao.deleteStory(story)
    override suspend fun updateStory(story: Story) = dao.updateStory(story)
}