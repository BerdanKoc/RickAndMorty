package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.network.Character
import com.example.myapplication.network.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {
    private val _characters = mutableListOf<Character>()
    val characters: List<Character> get() = _characters

    // Fonction pour récupérer les personnages
    fun fetchCharacters(onComplete: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = RetrofitInstance.api.getCharacters()
                response.enqueue(object : Callback<CharacterResponse> {
                    override fun onResponse(
                        call: Call<CharacterResponse>,
                        response: Response<CharacterResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.results?.let {
                                _characters.addAll(it)
                            }
                        }
                        onComplete()
                    }

                    override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                        // Gérer l'erreur
                        onComplete()
                    }
                })
            }
        }
    }
}
