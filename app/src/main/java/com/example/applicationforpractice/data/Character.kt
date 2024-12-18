package com.example.applicationforpractice.data

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val url: String,
    val name: String,
    val culture: String,
    val born: String,
    val titles: List<String>,
    val aliases: List<String>,
    val playedBy: List<String>
)



