package com.example.applicationforpractice

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class CharacterRepository(private val client: HttpClient) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getCharacters(page: Int, pageSize: Int): List<Character> {
        val response: HttpResponse = client.get("https://www.anapioficeandfire.com/api/characters?page=$page&pageSize=$pageSize")
        val jsonString = response.bodyAsText()
        return json.decodeFromString<List<Character>>(jsonString)
    }
}




