package com.example.applicationforpractice.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val users = mutableListOf<Pair<String, String>>()

    init {
        users.add(Pair("test@gmail.com", "123"))
        users.add(Pair("user@gmail.com", "password"))
        users.add(Pair("admin@gmail.com", "root"))
    }

    fun addUser(email: String, password: String) {
        Log.d("UserViewModel", "addUser: $email, $password")
        users.add(Pair(email, password))
        Log.d("UserViewModel", "addUser: $users")
    }

    fun isUserValid(email: String, password: String): Boolean {
        Log.d("UserViewModel", "isUserValid: $email, $password")
        Log.d("UserViewModel", "isUserValid: $users")
        return users.any { it.first == email && it.second == password }
    }
}
