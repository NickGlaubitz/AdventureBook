package com.example.adventurebook.data.remote

class OpenAiService(private val api: OpenAiApi) {

    suspend fun generateText(prompt: String): String {
        val systemMessage = ChatRequest.Message(
            role = "system",
            content = "Du bist ein Kinderbuchautor und schreibst spannende Geschichten für Kinder im Alter von 6 bis 12 Jahren."
        )
        val userMessage = ChatRequest.Message(role = "user", content = prompt)

        val request = ChatRequest(
            messages = listOf(systemMessage,userMessage)
        )

        val response = api.generateText(request)

        return response.choices.firstOrNull()?.message?.content?.trim() ?: "Fehler beim generieren der Geschichte"
    }

    suspend fun generateImage(prompt: String): String {
        val request = ImageRequest(prompt = "Eine Illustration für eine spannende Kinderbuchgeschichte: $prompt")

        val response = api.generateImage(request)

        return response.data.firstOrNull()?.url ?: "Fehler beim laden von Bildern"
    }
}