package com.example.adventurebook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Avatar::class, Story::class, Character::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun avatarDao(): AvatarDao
    abstract fun storyDao(): StoryDao
    abstract fun characterDao(): CharacterDao
}