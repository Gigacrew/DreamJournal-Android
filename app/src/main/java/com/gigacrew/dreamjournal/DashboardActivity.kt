package com.gigacrew.dreamjournal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.gigacrew.dreamjournal.databinding.ActivityDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DashboardActivity : AppCompatActivity(),
    DreamListAdapter.OnItemClickListener,
    DreamListAdapter.OnDeleteClickListener{

    private lateinit var menuButton: ImageButton
    private lateinit var welcomeText: TextView
    private lateinit var profileButton: ImageButton
    private lateinit var filterButton: ImageButton
    private lateinit var searchView: SearchView
    private lateinit var viewAllButton: Button
    private lateinit var addButton: Button

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var database: AppDatabase
    private lateinit var loggedInUser: User
    private val dreams: ArrayList<Dream> = ArrayList()
    private lateinit var dreamListAdapter:DreamListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)


        GlobalScope.launch(Dispatchers.Main) {
            loggedInUser = database.userDao().getUserById(intent.getIntExtra("userID", 0))!!

            fetchDreams()
            welcomeText.text = "Welcome Back, ${loggedInUser.firstname}"
        }

        // Initialize your views
        menuButton = binding.menuButton
        welcomeText = binding.welcomeText
        profileButton = binding.profileButton
        filterButton = binding.filterButton
        searchView = binding.searchView
        viewAllButton = binding.viewAllButton
        addButton = binding.addButton

        dreamListAdapter = DreamListAdapter(dreams,this,this)
        binding.dashboardRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.dashboardRecyclerView.adapter = dreamListAdapter






        menuButton.setOnClickListener { /* Handle menu button click */ }


        profileButton.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            intent.putExtra("userID",loggedInUser.user_id)
            startActivity(intent)
        }


        filterButton.setOnClickListener { /* Handle filter button click */ }

        binding.seeAllDreamsBtn.setOnClickListener{
            val intent = Intent(this,DreamListViewActivity::class.java)
            intent.putExtra("userID",loggedInUser.user_id)
            startActivity(intent)
        }
        viewAllButton.setOnClickListener {
            val intent = Intent(this,DreamListViewActivity::class.java)
            intent.putExtra("userID",loggedInUser.user_id)
            startActivity(intent)
        }


        addButton.setOnClickListener {
            val intent = Intent(this,AddNewDreamActivity::class.java)
            intent.putExtra("userID",loggedInUser.user_id)
            startActivity(intent)
        }


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

    }

    override fun onResume() {
        super.onResume()
        fetchDreams()
    }

    override fun onItemClick(dream: Dream) {
        val intent = Intent(this,AddNewDreamActivity::class.java)
        intent.putExtra("dreamID", dream.dream_id)
        intent.putExtra("userID",loggedInUser.user_id)
        startActivity(intent)
    }

    override fun onDeleteClick(dream: Dream) {
        TODO("Not yet implemented")
    }

    private fun fetchDreams() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = withContext(Dispatchers.IO) {
                    Log.i("Dreams", "User Logged In , $loggedInUser")
                    database.dreamDAO().getAllDreamsForUser(loggedInUser.user_id) //
                }
                dreams.clear()
                dreams.addAll(response)
                dreams.reverse()
                dreams.subList(3,dreams.size).clear()
                dreamListAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                displayErrorMessage("Failed to fetch dreams. Error: ${e.message}", "Fetch Error")
            }
        }
    }
    private fun displayErrorMessage(message: String,errorTag : String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
        Log.e(errorTag,"Failed to fetch dreams. Response code: $message" )
    }

}