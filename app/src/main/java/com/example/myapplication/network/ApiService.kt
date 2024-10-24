package com.example.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call

// Data model
data class Character(
    val id: Int,
    val name: String,
    val image: String
)

// Retrofit service interface
interface ApiService {
    @GET("character")
    fun getCharacters(): Call<CharacterResponse>
}

// Data model pour la réponse de l'API
data class CharacterResponse(
    val results: List<Character>
)

// Singleton pour initialiser Retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
