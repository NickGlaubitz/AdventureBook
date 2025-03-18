package com.example.adventurebook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Avatar(
    @PrimaryKey val id: Int = 1,

    val name: String,
    val gender: String,
    val age: String
)