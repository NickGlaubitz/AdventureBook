package com.example.adventurebook

import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.aallam.openai.api.image.internal.ImageResponseFormat.Companion.url


object Constants{
    const val OPENAI_API_KEY = "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"
}


// Animation für Bildübergang
//Crossfade(targetState = story!!.ImageUrl) { url ->
//    AsyncImage(
//        model = url,
//        contentDescription = "Story Image",
//        modifier = Modifier.fillMaxSize(),
//        contentScale = ContentScale.Crop
//    )
//}