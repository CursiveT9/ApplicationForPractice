package com.example.applicationforpractice.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.applicationforpractice.api.ApiRepository
import com.example.applicationforpractice.data.Character
import com.example.applicationforpractice.db.CharacterEntity
import com.example.applicationforpractice.db.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    companion object {
        private const val TAG = "CharacterViewModel"
    }

    private val pageSize = 50
    private var currentPage = 19

    fun setCurrentPage(page: Int) {
        currentPage = page
    }

    // LiveData для отображения списка персонажей на экране
    private val _characters = MutableLiveData<List<CharacterEntity>>()
    val characters: LiveData<List<CharacterEntity>> = _characters // Публичное свойство для наблюдения

    // Функция для загрузки данных для текущей страницы
    fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            try {
                // Получаем все персонажи из базы
                val allCharacters = repository.allCharacters.first()

                // Логируем количество всех персонажей
                Log.d("CharacterPage", "Total characters in DB: ${allCharacters.size}")

                // Рассчитываем диапазон ID для текущей страницы
                val pageSize = 50
                val minId = (page - 1) * pageSize + 1
                val maxId = page * pageSize

                // Фильтруем персонажей по диапазону ID
                val charactersForPage = allCharacters.filter { character ->
                    character.id in minId..maxId
                }

                // Логируем количество персонажей на текущей странице
                Log.d("CharacterPage", "Page $page - Characters count: ${charactersForPage.size}")

                if (charactersForPage.isEmpty()) {
                    // Если данных нет для этой страницы, загружаем с API
                    Log.d("CharacterPage", "Page $page - No data found. Fetching from API.")
                    val characterList = repository.fetchCharactersFromApi(page, pageSize)

                    // Вставляем данные в базу данных
                    withContext(Dispatchers.IO) {
                        repository.insertCharacters(characterList)
                    }

                    // Получаем обновленные данные для страницы
                    val updatedCharactersForPage = repository.getCharactersForPage(page)
                    _characters.postValue(updatedCharactersForPage) // Обновление LiveData
                } else {
                    // Если данные есть, просто обновляем LiveData
                    _characters.postValue(charactersForPage)
                }

                // Логируем всех персонажей из базы
                allCharacters.forEach { character ->
                    Log.d("Character", "ID: ${character.id}, Name: ${character.name}")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при загрузке персонажей", e)
            }
        }
    }



    // Переход на следующую страницу
    fun nextPage() {
        currentPage++
        fetchCharacters(currentPage)
    }

    // Переход на предыдущую страницу
    fun previousPage() {
        if (currentPage > 1) {
            currentPage--
            fetchCharacters(currentPage)
        }
    }

    // Получение текущей страницы
    fun getCurrentPage() = currentPage
}




