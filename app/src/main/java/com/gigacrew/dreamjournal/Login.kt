package com.gigacrew.dreamjournal

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    private lateinit var logUsernameEditText: EditText
    private lateinit var logPasswordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var appleLoginButton: ImageButton
    private lateinit var fbLoginButton: ImageButton
    private lateinit var googleLoginButton: ImageButton
    private lateinit var createAccountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logUsernameEditText = findViewById(R.id.logusername)
        logPasswordEditText = findViewById(R.id.logpass)
        loginButton = findViewById(R.id.loginbtn)
        forgotPasswordTextView = findViewById(R.id.fpass)
        appleLoginButton = findViewById(R.id.iloginbtn)
        fbLoginButton = findViewById(R.id.fbloginbtn)
        googleLoginButton = findViewById(R.id.googleloginbtn)
        createAccountTextView = findViewById(R.id.cracttv)

        // Add click listener for the login button
        loginButton.setOnClickListener {
            // Retrieve the entered username and password
            val username = logUsernameEditText.text.toString()
            val password = logPasswordEditText.text.toString()

            // Perform your login logic here, e.g., make an API call to validate credentials
            // For simplicity, let's just display a toast message indicating successful login
            if (isValidCredentials(username, password)) {
                showToast("Login successful!")
                // Navigate to the main activity or the next screen after successful login
            } else {
                showToast("Invalid credentials. Please try again.")
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
            // Implement the logic for creating a new account
            // For example, navigate to the registration activity
            showToast("Create account clicked.")
        }
    }

    // Placeholder method for validating credentials
    private fun isValidCredentials(username: String, password: String): Boolean {
        // Add your logic to validate the username and password
        // For demonstration purposes, let's assume valid credentials for "user123" and "pass123"
        return username == "user123" && password == "pass123"
    }

    // Helper method to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
