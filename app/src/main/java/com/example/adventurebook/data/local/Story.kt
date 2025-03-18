package com.example.adventurebook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Story(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val content: String,
    val ImageUrl: String,
    val createdAt: Long = System.currentTimeMillis()
)