package com.gigacrew.dreamjournal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DreamEntryTable")
data class Dream(
    @PrimaryKey(autoGenerate = true)
    val dream_id: Int = 0,
    val user_id: Int,
    val title: String,
    val recurringDream: Boolean,
    val imageURL: String,
    val feeling: String,
    val dream_description: String,
    val date: String,
    var category: String
)
