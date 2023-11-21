package com.gigacrew.dreamjournal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.gigacrew.dreamjournal.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var firstName: EditText
    private lateinit var lastName:EditText
    private lateinit var username:EditText
    private lateinit var email: EditText
    private lateinit var password:EditText
    private lateinit var confirmPassword: EditText
    private lateinit var continueBtn:Button

    private lateinit var database: AppDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)

        firstName = binding.firstNameEditText
        lastName = binding.lastNameEditText
        username = binding.usernameEditText

        email = binding.emailEditText
        password = binding.passwordEditText
        confirmPassword = binding.confirmPasswordEditText
        continueBtn = binding.continueBtn

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        continueBtn.setOnClickListener{
            val firstNameText = firstName.text.toString()
            val lastNameText = lastName.text.toString()
            val usernameText = username.text.toString()
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val confirmPasswordText = confirmPassword.text.toString()

            // Clear any previous errors
            clearFieldErrors()

            var hasError = false
            if (firstNameText.isNotEmpty() && lastNameText.isNotEmpty() && usernameText.isNotEmpty() && emailText.isNotEmpty() && passwordText.isNotEmpty() && confirmPasswordText.isNotEmpty()){
                if (passwordText == confirmPasswordText){
                    firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(this) {task ->
                            if (task.isSuccessful){

                                // User registration success
                                val user = firebaseAuth.currentUser

                                // Save additional user information to Firestore
                                if (user != null) {
                                    val userId = user.uid
                                    val userMap = hashMapOf(
                                        "username" to usernameText,
                                        "firstname" to firstNameText,
                                        "lastname" to lastNameText,
                                        "email" to emailText
                                    )


                                    // Store user information in firestore
                                    firebaseDB.collection("users").document(userId)
                                        .set(userMap)
                                        .addOnSuccessListener {
                                            Log.d("TAG", "User information added to Firestore.")
                                            // TODO: Handle successful registration and navigation
                                            showToast("User Registered Successfully")
                                            val intent = Intent(this,LoginActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e("TAG", "Error adding user information to Firestore: $e")
                                            // TODO: Handle registration failure
                                            showToast("Data not Registered ")
                                        }
                                }





                            }else{
                                showToast("Unable to Register User")
                            }

                        }
                }

            }else{
                showToast("Enter all the fields value")

            }
           /* Previous database code
            if (firstNameText.isEmpty()) {
                hasError = true
                firstName.error = "First name is required"
            }

            if (lastNameText.isEmpty()) {
                hasError = true
                lastName.error = "Last name is required"
            }
            if (usernameText.isEmpty()) {
                hasError = true
                username.error = "Last name is required"
            }

            if (emailText.isEmpty()) {
                hasError = true
                email.error = "Email is required"
            }

            if (passwordText.isEmpty()) {
                hasError = true
                password.error = "Password is required"
            }

            if (confirmPasswordText.isEmpty()) {
                hasError = true
                confirmPassword.error = "Confirm Password is required"
            } else if (confirmPasswordText != passwordText) {
                hasError = true
                confirmPassword.error = "Passwords do not match"
            }

            if (!hasError) {
                val user = User(username = usernameText, firstname = firstNameText, lastname = lastNameText, email = emailText, password = passwordText, phone_number = " " )

                GlobalScope.launch {
                    database.userDao().insertUser(user)
                    Log.i("UserCreation", "Created User $user")
                    Looper.prepare()
                    showToast("User Registered Successfully")
                    startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
                    finish()
                }
            }*/
        }
        binding.loginConnectorText.setOnClickListener{
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            finish()
        }

        }

    private fun clearFieldErrors() {
        firstName.error = null
        lastName.error = null
        email.error = null
        password.error = null
        confirmPassword.error = null
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
