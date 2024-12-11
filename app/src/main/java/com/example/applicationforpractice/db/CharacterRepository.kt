package com.example.applicationforpractice.db

import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val characterDao: CharacterDao) {

    val readAllCharacters: Flow<List<CharacterEntity>> = characterDao.readAllCharacters()

    fun insertCharacters(characters: List<CharacterEntity>) {
        characterDao.insertCharacters(characters)
    }

    fun deleteAllCharacters() {
        characterDao.deleteAllCharacters()
    }

    fun getCharacterById(id: Int): Flow<List<CharacterEntity>> {
        return characterDao.getCharacterById(id)
    }

    fun replaceCharacters(characters: List<CharacterEntity>) {
        characterDao.replaceCharacters(characters)
    }

}