package com.example.adventurebook.data.remote

class OpenAiService(private val api: OpenAiApi) {

    suspend fun generateText(prompt: String): String {
        val request = TextRequest(
            model = "gpt-4o",
            prompt = prompt,
            max_tokens = 1000
        )

        val response = api.generateText(request)

        return response.choices.firstOrNull()?.text ?: "Fehler beim generieren der Geschichte"
    }

    suspend fun generateImage(prompt: String): String {
        val request = ImageRequest(
            prompt = prompt,
            n = 1,
            size = "512x512"
        )

        val response = api.generateImage(request)

        return response.data.firstOrNull()?.url ?: "Fehler beim laden von Bilddatei"
    }
}