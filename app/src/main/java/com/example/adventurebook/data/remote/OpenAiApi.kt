package com.example.adventurebook.data.remote

import com.squareup.moshi.Json
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun generateText(@Body request: ChatRequest): ChatResponse

    @POST("v1/images/generations")
    suspend fun generateImage(@Body request: ImageRequest): ImageResponse
}

data class ChatRequest(
    @Json(name = "model") val model: String = "gpt-4o",
    @Json(name = "messages") val messages: List<Message>,
    @Json(name = "max_tokens") val maxTokens: Int = 1000
) {
    data class Message(
        @Json(name = "role") val role: String,
        @Json(name = "content") val content: String
    )
}



data class ChatResponse(
    @Json(name = "choices") val choices: List<Choice>
) {
    data class Choice(
        @Json(name = "message") val message: Message
    )
    data class Message(
        @Json(name = "content") val content: String
    )
}

data class ImageRequest(
    @Json(name = "model") val model: String = "dall-e-3",
    @Json(name = "prompt") val prompt: String,
    @Json(name = "n") val n: Int = 1,
    @Json(name = "size") val size: String = "1024x1024"
)

data class ImageResponse(
    @Json(name = "data") val data: List<ImageData>
) {
    data class ImageData(
        @Json(name = "url") val url: String
    )
}
