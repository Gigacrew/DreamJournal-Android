package com.gigacrew.dreamjournal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {

    // Insert a user into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // Delete a user into the database
    @Delete
    suspend fun deleteUser(user: User)

    // Get all users
    @Query(value = "SELECT * FROM UserTable")
    fun getAllUser(): LiveData<List<User>>

        // Get a user by ID
    @Query("SELECT * FROM UserTable WHERE user_id = :userId")
    suspend fun getUserById(userId: Int): User?

        // Update a user
    @Query("UPDATE UserTable SET username = :username, firstname = :firstname, " +
                "lastname = :lastname, email = :email, password = :password, " +
                "phone_number = :phoneNumber WHERE user_id = :userId")
    suspend fun updateUser(
            userId: Int,
            username: String,
            firstname: String,
            lastname: String,
            email: String,
            password: String,
            phoneNumber: String
        )
}