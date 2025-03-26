package com.example.adventurebook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val name: String
)