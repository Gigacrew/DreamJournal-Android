package com.gigacrew.dreamjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getDatabase(this)
        /*
        //Just to insert a value
        //  Insert code into global scope so that they can't be called from main thread.
        GlobalScope.launch {
            database.userDao().insertUser(User(user_id = 0, username = "Dummy", firstname = "Dummy",
            lastname = "Android", email = "abc@gmail.com", password = "1234", phone_number = "1234567890"))
        }
          */


    }
}