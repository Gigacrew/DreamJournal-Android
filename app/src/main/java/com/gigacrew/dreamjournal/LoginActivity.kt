package com.gigacrew.dreamjournal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gigacrew.dreamjournal.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

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

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logUsernameEditText = binding.loginUsernameEditText
        logPasswordEditText = binding.loginPasswordEditText
        loginButton = binding.loginBtn
        forgotPasswordTextView = binding.forgotPasswordTextField
        appleLoginButton = binding.appleLoginBtn
        fbLoginButton = binding.fbLoginBtn
        googleLoginButton = binding.googleLoginBtn
        createAccountTextView = binding.createAccountTextView

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()

        // Add click listener for the login button
        loginButton.setOnClickListener {
            // Retrieve the entered username and password
            val username = logUsernameEditText.text.toString()
            val password = logPasswordEditText.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()){

                // Perform a query to check if the username already exists
                firebaseDB.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents.isEmpty) {
                            showToast("Username is not found, you can proceed with registration")
                        } else {
                            // Username is already taken
                            Log.d("TAG", "Username is already taken.")
                            val currentUser: FirebaseUser? = firebaseAuth.currentUser
                            if (currentUser != null) {
                                // User is signed in, you can get the UID
                                val uid: String = currentUser.uid
                                showToast("User Login Successfully")
                                // Now 'uid' contains the user ID
                                // You can use this ID to uniquely identify the user in your Firebase Database or other services
                                // Retrieve user information
                                firebaseDB.collection("users").document(currentUser.uid)
                                    .get()
                                    .addOnSuccessListener { documentSnapshot ->
                                        if (documentSnapshot.exists()) {
                                            // Document exists, retrieve data
                                            val userName = documentSnapshot.getString("username")
                                            val firstName = documentSnapshot.getString("firstname")
                                            val lastName = documentSnapshot.getString("lastname")
                                            val email = documentSnapshot.getString("email")

                                            val intent = Intent(this, DashboardActivity::class.java)
                                            intent.putExtra("userID", uid)
                                            intent.putExtra("firstName", firstName)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            showToast("User does not exist")
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("TAG", "Error retrieving user information: $e")
                                    }
                            }

                        }
                    }
            }else{
                showToast("Enter all the fields value")
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
