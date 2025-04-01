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