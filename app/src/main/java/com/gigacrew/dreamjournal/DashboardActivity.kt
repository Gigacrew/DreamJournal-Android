import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.core.view.isVisible
import com.gigacrew.dreamjournal.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class DashboardActivity : AppCompatActivity() {

    private lateinit var menuButton: ImageButton
    private lateinit var welcomeText: TextView
    private lateinit var profileButton: ImageButton
    private lateinit var filterButton: ImageButton
    private lateinit var searchView: SearchView
    private lateinit var viewAllButton: Button
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize your views
        menuButton = findViewById(R.id.menuButton)
        welcomeText = findViewById(R.id.welcomeText)
        profileButton = findViewById(R.id.profileButton)
        filterButton = findViewById(R.id.filterButton)
        searchView = findViewById(R.id.searchView)
        viewAllButton = findViewById(R.id.viewAllButton)
        addButton = findViewById(R.id.addButton)
        recyclerView = findViewById(R.id.recyclerView)

        // Set up RecyclerView
        val layoutManager: LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        menuButton.setOnClickListener { /* Handle menu button click */ }


        profileButton.setOnClickListener { /* Handle profile button click */ }


        filterButton.setOnClickListener { /* Handle filter button click */ }


        viewAllButton.setOnClickListener { /* Handle view all button click */ }


        addButton.setOnClickListener {/* Handle add button click */ }



        // Set a query listener for the search view
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query change
                return true
            }
        })

        val adapter = RecyclerViewAdapter() // Replace with your adapter class
        recyclerView.adapter = adapter
    }
}

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



}
