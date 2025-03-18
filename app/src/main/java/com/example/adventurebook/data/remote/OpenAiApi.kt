package com.example.adventurebook.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun generateText(@Body request: ChatRequest): ChatResponse

    @POST("v1/images/generations")
    suspend fun generateImage(@Body request: ImageRequest): ImageResponse
}

data class ChatRequest(
    val model: String = "gpt-4o",
    val messages: List<Message>,
    val max_tokens: Int = 1000
) {
    data class Message(
        val role: String,
        val content: String
    )
}



data class ChatResponse(
    val choices: List<Choice>
) {
    data class Choice(val message: Message)
    data class Message(val content: String)
}

data class ImageRequest(
    val model: String = "dall-e-3",
    val prompt: String,
    val n: Int = 1,
    val size: String = "512x512"
)

data class ImageResponse(
    val data: List<ImageData>
) {
    data class ImageData(val url: String)
}
