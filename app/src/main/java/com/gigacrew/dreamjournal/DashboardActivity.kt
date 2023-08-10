import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout and return a ViewHolder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_dashboard, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the views inside the ViewHolder
        // You can use 'position' to retrieve the corresponding data
    }

    override fun getItemCount(): Int {
        // Return the number of items in your data set
        return 0 // Replace with the actual count
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize your views here
    }
}
