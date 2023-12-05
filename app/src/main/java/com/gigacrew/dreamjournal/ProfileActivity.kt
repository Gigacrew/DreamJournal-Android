package com.gigacrew.dreamjournal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gigacrew.dreamjournal.databinding.ActivityProfileBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: FirebaseFirestore
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseFirestore.getInstance()

        userId = intent.getStringExtra("userID") ?: ""

        binding.toolbar.toolbarTitle.setText(R.string.update_profile)
        binding.toolbar.leftActionButton.setImageResource(R.drawable.ic_back)

        binding.toolbar.leftActionButton.setOnClickListener {
            finish()
        }

        if (userId.isNotEmpty()) {
            database.collection("users").document(userId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    user?.let { updateUIWithUserData(it) }
                }
        }

        binding.updateButton.setOnClickListener {
            val updatedUser = hashMapOf(
                "username" to binding.usernameEditText.text.toString(),
                "firstname" to binding.firstNameEditText.text.toString(),
                "lastname" to binding.lastNameEditText.text.toString(),
                "phoneNumber" to binding.phoneNumberEditText.text.toString()
            )

            database.collection("users").document(userId).update(updatedUser as Map<String, Any>)
                .addOnSuccessListener { finish() }
                .addOnFailureListener { e -> /* Handle failure */ }
        }

        binding.addJournalEntryButton.setOnClickListener {
            val intent = Intent(this, AddNewDreamActivity::class.java)
            intent.putExtra("userID", userId)
            startActivity(intent)
        }
        binding.toolbar.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun updateUIWithUserData(user: User) {
        binding.usernameEditText.setText(user.username)
        binding.firstNameEditText.setText(user.firstname)
        binding.lastNameEditText.setText(user.lastname)
        binding.phoneNumberEditText.setText(user.phoneNumber)

    }

    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
