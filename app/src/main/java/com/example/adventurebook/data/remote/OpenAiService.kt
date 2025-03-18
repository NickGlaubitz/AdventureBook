package com.example.adventurebook.data.remote

class OpenAiService(private val api: OpenAiApi) {

    suspend fun generateText(prompt: String): String {
        val request = ChatRequest(
            messages = listOf(ChatRequest.Message(role = "user", content = prompt))
        )

        val response = api.generateText(request)

        return response.choices.firstOrNull()?.message?.content ?: "Fehler beim generieren der Geschichte"
    }

    suspend fun generateImage(prompt: String): String {
        val request = ImageRequest(prompt = prompt)

        val response = api.generateImage(request)

        return response.data.firstOrNull()?.url ?: "Fehler beim laden von Bildern"
    }
}