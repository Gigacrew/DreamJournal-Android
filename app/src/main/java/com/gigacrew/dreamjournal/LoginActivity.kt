package com.gigacrew.dreamjournal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gigacrew.dreamjournal.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var logUsernameEditText: EditText
    private lateinit var logPasswordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var appleLoginButton: ImageButton
    private lateinit var fbLoginButton: ImageButton
    private lateinit var googleLoginButton: ImageButton
    private lateinit var createAccountTextView: TextView
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        logUsernameEditText = binding.loginUsernameEditText
        logPasswordEditText = binding.loginPasswordEditText
        loginButton = binding.loginBtn
        forgotPasswordTextView = binding.forgotPasswordTextField
        appleLoginButton = binding.appleLoginBtn
        fbLoginButton = binding.fbLoginBtn
        googleLoginButton = binding.googleLoginBtn
        createAccountTextView = binding.createAccountTextView



        // Add click listener for the login button
        loginButton.setOnClickListener {
            // Retrieve the entered username and password
            val username = logUsernameEditText.text.toString()
            val password = logPasswordEditText.text.toString()

            GlobalScope.launch(Dispatchers.IO) {
                val user = database.userDao().getUserByLoginCredentials(username, password)
                if (user != null) {
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.putExtra("userID", user.user_id)
                    startActivity(intent)
                } else {
                    showToast("Invalid credentials. Please try again.")
                }
            }
        }

        // Add click listener for the forgot password TextView
        forgotPasswordTextView.setOnClickListener {
            // Implement the logic for handling the forgot password functionality
            // For example, show a dialog or navigate to the forgot password activity
            showToast("Forgot password clicked.")
        }

        // Add click listeners for the social login buttons (Apple, Facebook, Google)
        appleLoginButton.setOnClickListener {
            // Implement the logic for handling Apple login
            showToast("Apple login clicked.")
        }

        fbLoginButton.setOnClickListener {
            // Implement the logic for handling Facebook login
            showToast("Facebook login clicked.")
        }

        googleLoginButton.setOnClickListener {
            // Implement the logic for handling Google login
            showToast("Google login clicked.")
        }

        // Add click listener for the create account TextView
        createAccountTextView.setOnClickListener {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }
    }


    // Helper method to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
