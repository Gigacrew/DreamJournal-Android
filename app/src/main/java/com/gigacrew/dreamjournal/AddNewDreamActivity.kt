package com.gigacrew.dreamjournal

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddNewDreamActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var checkboxHappy: CheckBox
    private lateinit var checkboxSad: CheckBox
    private lateinit var checkboxAngry: CheckBox
    private lateinit var checkboxGuilt: CheckBox
    private lateinit var checkboxFear: CheckBox
    private lateinit var checkboxAnxiety: CheckBox
    private lateinit var uploadButton: Button
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newdream)

        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        checkboxHappy = findViewById(R.id.checkboxHappy)
        checkboxSad = findViewById(R.id.checkboxSad)
        checkboxAngry = findViewById(R.id.checkboxAngry)
        checkboxGuilt = findViewById(R.id.checkboxGuilt)
        checkboxFear = findViewById(R.id.checkboxFear)
        checkboxAnxiety = findViewById(R.id.checkboxAnxiety)
        uploadButton = findViewById(R.id.uploadButton)
        saveButton = findViewById(R.id.saveButton)


        uploadButton.setOnClickListener {
            // code for upload image functionality
            showToast("Upload image functionality not implemented.")
        }

        saveButton.setOnClickListener {

            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val isHappy = checkboxHappy.isChecked
            val isSad = checkboxSad.isChecked
            val isAngry = checkboxAngry.isChecked
            val isGuilt = checkboxGuilt.isChecked
            val isFear = checkboxFear.isChecked
            val isAnxiety = checkboxAnxiety.isChecked

            showToast("Data saved successfully.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
