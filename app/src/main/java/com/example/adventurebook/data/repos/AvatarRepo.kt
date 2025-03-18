package com.example.adventurebook.data.repos

import com.example.adventurebook.data.local.Avatar
import com.example.adventurebook.data.local.AvatarDao

interface AvatarRepoInterface {
    suspend fun getAvatar(): Avatar?
    suspend fun insertAvatar(avatar: Avatar)
}

class AvatarRepoImpl(private val dao: AvatarDao): AvatarRepoInterface {
    override suspend fun getAvatar(): Avatar? = dao.getAvatar()
    override suspend fun insertAvatar(avatar: Avatar) = dao.insertAvatar(avatar)
}