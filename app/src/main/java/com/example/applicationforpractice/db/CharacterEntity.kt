package com.example.applicationforpractice.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val culture: String?,
    val born: String?,
    val titles: String?,
    val aliases: String?,
    val playedBy: String?
)