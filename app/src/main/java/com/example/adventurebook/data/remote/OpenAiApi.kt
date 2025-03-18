package com.example.adventurebook.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/completions")
    suspend fun generateText(@Body request: TextRequest): TextResponse

    @POST("v1/images/generations")
    suspend fun generateImage(@Body request: ImageRequest): ImageResponse
}

data class TextRequest(
    val model: String,
    val prompt: String,
    val max_tokens: Int
)

data class TextResponse(
    val choices: List<Choice>
) {
    data class Choice(val text: String)
}

data class ImageRequest(
    val prompt: String,
    val n: Int,
    val size: String
)

data class ImageResponse(
    val data: List<ImageData>
) {
    data class ImageData(val url: String)
}
