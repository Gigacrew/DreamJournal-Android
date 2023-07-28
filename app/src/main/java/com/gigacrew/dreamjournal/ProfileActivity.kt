import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gigacrew.dreamjournal.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var editLabel: TextView
    private lateinit var usernameTextfield: EditText
    private lateinit var firstNameTextfield: EditText
    private lateinit var lastNameTextfield: EditText
    private lateinit var phoneNumberTextfield: EditText
    private lateinit var updateButton: Button
    private lateinit var addNewJournalButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
     /*   editLabel = findViewById(R.id.editLabel)
        usernameTextfield = findViewById(R.id.usernameTextfield)
        firstNameTextfield = findViewById(R.id.firstNameTextfield)
        lastNameTextfield = findViewById(R.id.lastNameTextfield)
        phoneNumberTextfield = findViewById(R.id.phoneNumberTextfield)
        updateButton = findViewById(R.id.updateButton)
        addNewJournalButton = findViewById(R.id.addNewJournalButton) */

        // Retrieve data from intent or other sources and set them to the EditTexts
        val username = "" // Get the username
        val firstName = "" // Get the first name
        val lastName = "" // Get the last name
        val phoneNumber = "" // Get the phone number

        usernameTextfield.setText(username)
        firstNameTextfield.setText(firstName)
        lastNameTextfield.setText(lastName)
        phoneNumberTextfield.setText(phoneNumber)

        // Set click listeners for buttons (updateButton and addNewJournalButton) if needed
        updateButton.setOnClickListener {
            // Perform actions when updateButton is clicked
            // Implement your update logic here
        }

        addNewJournalButton.setOnClickListener {
            // Perform actions when addNewJournalButton is clicked
            // Implement your "Add New Journal Entry" logic here
        }
    }
}
