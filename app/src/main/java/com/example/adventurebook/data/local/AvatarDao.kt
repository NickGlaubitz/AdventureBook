package com.example.adventurebook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AvatarDao {

    @Query("SELECT * FROM avatar WHERE id = 1")
    suspend fun getAvatar(): Avatar?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAvatar(avatar: Avatar)
}