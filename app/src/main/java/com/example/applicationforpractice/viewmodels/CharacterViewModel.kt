package com.example.applicationforpractice.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationforpractice.api.CharacterRepository
import com.example.applicationforpractice.data.Character
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    private val pageSize = 50
    private var currentPage = 901/pageSize + 1

    fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            try {
                val characterList = repository.getCharacters(page, pageSize)
                _characters.value = characterList
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }

    fun nextPage() {
        currentPage++
        fetchCharacters(currentPage)
    }

    fun previousPage() {
        if (currentPage > 1) {
            currentPage--
            fetchCharacters(currentPage)
        }
    }

    fun getCurrentPage() = currentPage
}




