package com.gigacrew.dreamjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseFirestore.getInstance()

    }
}