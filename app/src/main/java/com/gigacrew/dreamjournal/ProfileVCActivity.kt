import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gigacrew.dreamjournal.R

class ProfileVCActivity : AppCompatActivity() {

    private lateinit var textView2: TextView
    private lateinit var searchView: SearchView
    private lateinit var view: View
    private lateinit var tableView: RecyclerView
    private lateinit var imageButton: ImageButton
    private lateinit var button: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var imageButton2: ImageButton
    private lateinit var imageButton3: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilevc)

        // Connect views to their corresponding XML elements using findViewById
        textView2 = findViewById(R.id.textView2)
        searchView = findViewById(R.id.searchView)
        view = findViewById(R.id.view)
        tableView = findViewById(R.id.tableView)
        imageButton = findViewById(R.id.imageButton)
        button = findViewById(R.id.button)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton3 = findViewById(R.id.imageButton3)

        // Now you can work with these views as needed, e.g., set listeners, update data, etc.
        button.setOnClickListener {
            // Your code for handling the button click goes here
        }

        // ...
    }
}
