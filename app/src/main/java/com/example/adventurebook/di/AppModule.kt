package com.example.adventurebook.di

import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import coil3.decode.Decoder
import coil3.network.NetworkFetcher
import com.example.adventurebook.data.local.AppDatabase
import com.example.adventurebook.data.local.Avatar
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database").build()
    }

    single { get<AppDatabase>().avatarDao() }
    single { get<AppDatabase>().storyDao() }
}

val repositoryModule = module {
    single<AvatarRepo> {
        AvatarRepoImpl(get())
    }

    single<StoryRepo> {
        StoryRepoImpl(get())
    }
}

val viewModelModule = module {
    viewModel { OnboardingViewModel(get()) }

    viewModel { StoryViewModel(get(), get()) }
}

val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OpenAiApi::class.java)
    }

    single { OpenAiService(get()) }
}