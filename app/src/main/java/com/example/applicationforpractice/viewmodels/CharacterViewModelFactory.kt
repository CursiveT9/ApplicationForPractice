package com.example.applicationforpractice.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.applicationforpractice.api.CharacterRepository

class CharacterViewModelFactory(private val repository: CharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
