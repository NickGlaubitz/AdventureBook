package com.example.adventurebook.di

import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import coil3.decode.Decoder
import coil3.network.NetworkFetcher
import com.example.adventurebook.Constants
import com.example.adventurebook.data.local.AppDatabase
import com.example.adventurebook.data.local.Avatar
import com.example.adventurebook.data.remote.OpenAiApi
import com.example.adventurebook.data.remote.OpenAiService
import com.example.adventurebook.data.repos.AvatarRepoImpl
import com.example.adventurebook.data.repos.AvatarRepoInterface
import com.example.adventurebook.data.repos.CharacterRepoImpl
import com.example.adventurebook.data.repos.CharacterRepoInterface
import com.example.adventurebook.data.repos.StoryRepoImpl
import com.example.adventurebook.data.repos.StoryRepoInterface
import com.example.adventurebook.data.viewmodel.OnboardingViewModel
import com.example.adventurebook.data.viewmodel.StoryViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database").build()
    }

    single { get<AppDatabase>().avatarDao() }
    single { get<AppDatabase>().storyDao() }
    single { get<AppDatabase>().characterDao() }
}

val repositoryModule = module {
    single<AvatarRepoInterface> {
        AvatarRepoImpl(get())
    }

    single<StoryRepoInterface> {
        StoryRepoImpl(get())
    }

    single<CharacterRepoInterface> {
        CharacterRepoImpl(get())
    }
}

val viewModelModule = module {
    viewModel { OnboardingViewModel(get()) }

    viewModel { StoryViewModel(get(), get(), get()) }
}

val apiModule = module {
    single {
        val client = OkHttpClient.Builder().addInterceptor { chain ->

            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${Constants.OPENAI_API_KEY}").build()

            chain.proceed(request)
        }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OpenAiApi::class.java)
    }

    single { OpenAiService(get()) }
}