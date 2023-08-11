package com.gigacrew.dreamjournal
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gigacrew.dreamjournal.R
import com.gigacrew.dreamjournal.databinding.ActivityProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: AppDatabase
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            // Retrieve user data from the database on the IO dispatcher
            user = withContext(Dispatchers.IO) {
                database.userDao().getUserById(intent.getIntExtra("userID", 0))
            }!!
            updateUIWithUserData()
        }




        // Set click listeners for buttons (updateButton and addNewJournalButton) if needed
        binding.updateButton.setOnClickListener {
            GlobalScope.launch {
                database.userDao().updateUser(
                    userId = user.user_id,
                    username= binding.usernameEditText.text.toString(),
                    email = user.email,
                    firstname = binding.firstNameEditText.text.toString(),
                    lastname = binding.lastNameEditText.text.toString(),
                    phoneNumber = binding.phoneNumberEditText.text.toString(),
                    password = user.password
                )
            }
            finish()
        }

        binding.addJournalEntryButton.setOnClickListener {
          val intent = Intent(this,AddNewDreamActivity::class.java)
            intent.putExtra("userID",user.user_id)
            startActivity(intent)
            finish()

        }
    }
        private fun updateUIWithUserData() {
            val username = user.username
            val firstName = user.firstname
            val lastName = user.lastname
            val phoneNumber = user.phone_number

            binding.usernameEditText.setText(username)
            binding.firstNameEditText.setText(firstName)
            binding.lastNameEditText.setText(lastName)
            binding.phoneNumberEditText.setText(phoneNumber)
        }
}
