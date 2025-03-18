package com.example.adventurebook.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adventurebook.data.local.Avatar
import com.example.adventurebook.data.repos.AvatarRepoInterface

class OnboardingViewModel(private val avatarRepo: AvatarRepoInterface): ViewModel() {
    suspend fun saveAvatar(name: String, gender: String, age: String) {
        val avatar = Avatar(
            name = name,
            gender = gender,
            age = age
        )

        avatarRepo.insertAvatar(avatar)
    }

    suspend fun getAvatar(): Avatar? = avatarRepo.getAvatar()
}