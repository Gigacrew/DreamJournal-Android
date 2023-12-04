package com.gigacrew.dreamjournal

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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
    private lateinit var database: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewdreamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Get the user_id from the intent that we get here from

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val dreamID = intent.getStringExtra("dreamID") ?:""
        Log.d("Dream ID",dreamID)

        val stringValue = if (dreamID.isNotEmpty())
            getString(R.string.update_dream)
        else
            getString(R.string.add_new_dream)
        binding.toolbar.toolbarTitle.text = stringValue
        binding.toolbar.leftActionButton.setImageResource(R.drawable.ic_back)

        binding.toolbar.leftActionButton.setOnClickListener {
            finish()
        }

        database = FirebaseFirestore.getInstance()
        val dreamsCollection = database.collection("dreams")
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

        if (dreamID.isNotEmpty()) {
           dreamsCollection
                .document(dreamID)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val dream = document.toObject(Dream::class.java)
                        dream?.let { setUpdateFields(it) }
                    } else {
                        // Handle the case where the dream doesn't exist.
                    }
                }.addOnFailureListener {
                    // Handle any failures here.
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

            if (dreamID.isNotEmpty()) {
                 val dream = hashMapOf(
                     "dreamId" to dreamID,
                     "title" to title,
                     "dreamDescription" to description,
                     "recurringDream" to recurringDream,
                     "feeling" to feelingsCheckedArray,
                     "category" to selectedItem,
                     "date" to getCurrentDateTimeFormatted(),
                     "imageURL" to ""
                 )
                dreamsCollection.document(dreamID).set(dream, SetOptions.merge())
                       .addOnSuccessListener {
                           showToast("Dream Updated Successfully")
                           val intent = Intent(this@AddNewDreamActivity,DashboardActivity::class.java)
                           intent.putExtra("userID",userID)
                           startActivity(intent)
                           finish()
                       }

            } else {
                val newDream = hashMapOf(
                    "title" to title,
                    "dreamDescription" to description,
                    "recurringDream" to recurringDream,
                    "feeling" to feelingsCheckedArray,
                    "category" to selectedItem,
                    "date" to getCurrentDateTimeFormatted(),
                    "imageURL" to "",
                    "userId" to userID
                )

                // Adding the new dream to Firestore and letting Firestore generate a unique ID
                dreamsCollection.add(newDream)
                    .addOnSuccessListener { documentReference ->
                        showToast("Dream Added Successfully with ID: ${documentReference.id}")
                        val intent = Intent(this@AddNewDreamActivity, DashboardActivity::class.java)
                        intent.putExtra("userID", userID)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        showToast("Error adding dream: ${e.message}")
                    }
            }
        }
        binding.toolbar.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun setUpdateFields(dream: Dream) {
        titleEditText.setText(dream.title)
        descriptionEditText.setText(dream.dreamDescription)
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
    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
