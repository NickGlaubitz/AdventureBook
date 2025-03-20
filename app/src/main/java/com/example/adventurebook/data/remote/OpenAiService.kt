package com.example.adventurebook.data.remote

import android.util.Log
import retrofit2.HttpException

class OpenAiService(private val api: OpenAiApi) {

    suspend fun generateText(prompt: String): String {
        val systemMessage = ChatRequest.Message(
            role = "system",
            content = "Du bist ein Kinderbuchautor und schreibst spannende Geschichten für Kinder im Alter von 6 bis 12 Jahren."
        )
        val userMessage = ChatRequest.Message(role = "user", content = prompt)

        val request = ChatRequest(
            model = "gpt-4o",
            messages = listOf(systemMessage, userMessage),
            maxTokens = 1000
        )

        return try {
            Log.d("OpenAiService", "Text Request: $request")
            val response = api.generateText(request)
            Log.d("OpenAiService", "Text Response: $response")
            response.choices.firstOrNull()?.message?.content?.trim()
                ?: "Fehler beim generieren der Geschichte"
        } catch (e: HttpException) {
            Log.e("OpenAiService", "Text Generation failed: HTTP ${e.code()} - ${e.response()?.errorBody()?.string()}")
            "Fehler beim Generieren der Geschichte"

        } catch (e: Exception) {
            Log.e("OpenAiService", "Text Generation failed: ${e.message}")
            "Fehler beim generieren der Geschichte"
        }
    }

    suspend fun generateImage(prompt: String): String {
        val request = ImageRequest(model = "dall-e-3", prompt = "Eine Illustration für eine spannende Kinderbuchgeschichte: $prompt", n = 1, size = "1024x1024")

        return try {
            Log.d("OpenAiService", "Image Request: $request")
            val response = api.generateImage(request)
            Log.d("OpenAiService", "Image Response: $response")
            response.data.firstOrNull()?.url ?: "Fehler beim laden von Bildern"
        } catch (e: HttpException) {
            Log.e("OpenAiService", "Image Generation failed: HTTP ${e.code()} - ${e.response()?.errorBody()?.string()}")
            "Fehler beim Generieren des Bildes"
        } catch (e: Exception) {
            Log.e("OpenAiService", "Image Generation failed: ${e.message}")
            "Fehler beim erzeugen des Bildes"
        }
    }
}

// package:mine