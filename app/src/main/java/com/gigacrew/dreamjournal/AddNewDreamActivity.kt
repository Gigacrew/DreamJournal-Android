package com.gigacrew.dreamjournal

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.collection.ArrayMap
import com.gigacrew.dreamjournal.databinding.ActivityNewdreamBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    private lateinit var recurringSwitch: SwitchCompat
    private lateinit var category: Spinner

    private lateinit var binding: ActivityNewdreamBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewdreamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Get the user_id from the intent that we get here from

        val userID = intent.getIntExtra("userID",0)
        val dreamID = intent.getIntExtra("dreamID",0)


        database = AppDatabase.getDatabase(this)
        titleEditText = binding.titleEditText
        descriptionEditText = binding.descriptionEditText
        recurringSwitch = binding.recurringDreamSwitch
        checkboxHappy = binding.checkboxHappy
        checkboxSad = binding.checkboxSad
        checkboxAngry = binding.checkboxAngry
        checkboxGuilt = binding.checkboxGuilt
        checkboxFear = binding.checkboxFear
        checkboxAnxiety = binding.checkboxAnxiety
        category = binding.categorySpinner
        uploadButton = binding.uploadButton
        saveButton = binding.saveButton


        if (dreamID > 0) {
            GlobalScope.launch(Dispatchers.IO) {
                 val dream = database.dreamDAO().getDreamById(dreamID)!!
                setUpdateFields(dream)
            }

        }

        var selectedItem = ""
        val categoryOptions = resources.getStringArray(R.array.dream_categories)

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,categoryOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

      
        category.adapter = adapter

        category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                if (position == 0) {
                    return
                }
                 selectedItem = parent.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing here
            }
        }
        uploadButton.setOnClickListener {
            // code for upload image functionality
            showToast("Upload image functionality not implemented.")
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val recurringDream = recurringSwitch.isChecked

            if (title.isEmpty() || description.isEmpty()) {
                // Show an error message or toast indicating validation failure
                showToast("Title and description are required.")
                return@setOnClickListener
            }
            // Create an ArrayMap to hold the emotions and their checkbox values
            val feelingsChecked = ArrayMap<String, Boolean>().apply {
                put("Happy", checkboxHappy.isChecked)
                put("Sad", checkboxSad.isChecked)
                put("Angry", checkboxAngry.isChecked)
                put("Guilt", checkboxGuilt.isChecked)
                put("Fear", checkboxFear.isChecked)
                put("Anxiety", checkboxAnxiety.isChecked)
            }

            // Build the feelingsCheckedArray using the selected emotions
            val feelingsCheckedArray = ArrayList<String>(buildFeelingsArray(feelingsChecked))

            if (dreamID > 0) {
                GlobalScope.launch(Dispatchers.IO) {
                    database.dreamDAO().updateDream(
                        dreamId = dreamID,
                        title = title,
                        dreamDescription = description,
                        recurringDream = recurringDream,
                        feeling = feelingsCheckedArray,
                        category = selectedItem,
                        date = getCurrentDateTimeFormatted(),
                        imageURL = "",
                    )

                }
                showToast("Dream Updated Successfully")
                val intent = Intent(this@AddNewDreamActivity,DashboardActivity::class.java)
                intent.putExtra("userID",userID)
                startActivity(intent)
                finish()
            } else {
                val newDream = Dream(
                    title = title,
                    dream_description = description,
                    recurringDream = recurringDream,
                    feeling = feelingsCheckedArray,
                    category = selectedItem,
                    date = getCurrentDateTimeFormatted(),
                    imageURL = "",
                    user_id = userID
                )
                GlobalScope.launch(Dispatchers.IO) {
                    database.dreamDAO().insertDream(newDream)
                }
                showToast("Dream Added Successfully")
                val intent = Intent(this@AddNewDreamActivity,DashboardActivity::class.java)
                intent.putExtra("userID",userID)
                startActivity(intent)
               finish()
            }
        }
    }

    private fun setUpdateFields(dream: Dream) {
        titleEditText.setText(dream.title)
        descriptionEditText.setText(dream.dream_description)
        recurringSwitch.isChecked = dream.recurringDream
        // Set checkboxes based on the feelings in the dream
        checkboxHappy.isChecked = dream.feeling.contains("Happy")
        checkboxSad.isChecked = dream.feeling.contains("Sad")
        checkboxAngry.isChecked = dream.feeling.contains("Angry")
        checkboxGuilt.isChecked = dream.feeling.contains("Guilt")
        checkboxFear.isChecked = dream.feeling.contains("Fear")
        checkboxAnxiety.isChecked = dream.feeling.contains("Anxiety")
        // Set the selected category in the spinner
        val categoryIndex = resources.getStringArray(R.array.dream_categories).indexOf(dream.category)
        category.setSelection(categoryIndex)


    }

    private fun getCurrentDateTimeFormatted(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
    private fun buildFeelingsArray(feelingsChecked: ArrayMap<String, Boolean>): List<String> {
        val selectedFeelings = mutableListOf<String>()

        for ((feeling, isChecked) in feelingsChecked) {
            if (isChecked) {
                selectedFeelings.add(feeling)
            }
        }
        return selectedFeelings
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
