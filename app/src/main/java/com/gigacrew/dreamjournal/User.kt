package com.gigacrew.dreamjournal

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey(autoGenerate = true)
    val user_id : Int = 0,
    val username : String,
    val firstname : String,
    val lastname : String,
    val email : String,
    val password : String,
    val phone_number : String

)
