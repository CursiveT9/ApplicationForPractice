package com.example.applicationforpractice.db

import com.example.applicationforpractice.api.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CharacterRepository(
    private val characterDao: CharacterDao,
    private val apiRepository: ApiRepository
) {
    val allCharacters: Flow<List<CharacterEntity>> = characterDao.readAllCharacters()
    private val pageSize = 50

    // Проверка, есть ли данные для конкретной страницы в базе данных
    suspend fun isPageLoaded(page: Int): Boolean {
        val offset = (page - 1) * pageSize
        val characters = characterDao.readAllCharacters().first()
        return characters.size > offset
    }

    suspend fun fetchCharactersFromApi(page: Int, pageSize: Int): List<CharacterEntity> {
        val charactersFromApi = apiRepository.getCharacters(page, pageSize)
        return charactersFromApi.map {
            CharacterEntity(
                id = it.url.split("/").last().toIntOrNull() ?: 0,
                name = it.name,
                culture = it.culture,
                born = it.born,
                titles = it.titles.joinToString(", "),
                aliases = it.aliases.joinToString(", "),
                playedBy = it.playedBy.joinToString(", ")
            )
        }
    }

    suspend fun insertCharacters(characters: List<CharacterEntity>) {
        characterDao.insertCharacters(characters)
    }

    suspend fun getCharactersForPage(page: Int): List<CharacterEntity> {
        val pageSize = 50
        // Рассчитываем диапазон ID для текущей страницы
        val minId = (page - 1) * pageSize + 1
        val maxId = page * pageSize

        // Получаем все персонажи из базы
        val allCharacters = characterDao.readAllCharacters().first()

        // Фильтруем персонажей по ID
        return allCharacters.filter { character ->
            character.id in minId..maxId
        }
    }



}
