package com.gigacrew.dreamjournal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DreamDAO {
    // Insert a dream into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertDream(dream: Dream)

    // Get a dream by ID
    @Query("SELECT * FROM DreamEntryTable WHERE dream_id = :dreamId")
   suspend fun getDreamById(dreamId: Int): Dream?

    // Get all dreams for a specific user
    @Query("SELECT * FROM DreamEntryTable WHERE user_id = :userId")
    fun getAllDreamsForUser(userId: Int): List<Dream>

    // Delete a dream
    @Query("DELETE FROM DreamEntryTable WHERE dream_id = :dreamId")
   suspend fun deleteDream(dreamId: Int)

    // Update a dream
    @Query("UPDATE DreamEntryTable SET title = :title, recurringDream = :recurringDream, " +
            "imageURL = :imageURL, feeling = :feeling, dream_description = :dreamDescription, " +
            "category = :category, date = :date " +
            "WHERE dream_id = :dreamId")
     suspend fun updateDream(
        dreamId: Int,
        title: String,
        recurringDream: Boolean,
        imageURL: String,
        feeling: ArrayList<String>,
        dreamDescription: String,
        category: String,
        date: String
    )

}