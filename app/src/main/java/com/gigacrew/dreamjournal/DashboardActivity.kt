package com.gigacrew.dreamjournal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.SearchView

import android.widget.Toast
import android.widget.Toolbar


import androidx.recyclerview.widget.LinearLayoutManager

import com.gigacrew.dreamjournal.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class DashboardActivity : AppCompatActivity(),
    DreamListAdapter.OnItemClickListener,
    DreamListAdapter.OnDeleteClickListener {

    private lateinit var profileButton: ImageButton
    private lateinit var searchView: SearchView
    private lateinit var addButton: ImageButton


    private lateinit var binding: ActivityDashboardBinding
    private lateinit var database: FirebaseFirestore
    private lateinit var loggedInUser: FirebaseUser
    private val dreams: ArrayList<Dream> = ArrayList()
    private lateinit var dreamListAdapter: DreamListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseFirestore.getInstance()


        loggedInUser = FirebaseAuth.getInstance().currentUser!!
        val userId = loggedInUser.uid
        if (userId.isNotEmpty()) {
            database.collection("users").document(userId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    if (user != null) {
                        binding.toolbar.toolbarTitle.setText("Welcome Back, ${user.username}")
                    }
                }
        }
        // Initialize your views


        profileButton = binding.profileButton
        searchView = binding.searchView
        addButton = binding.addButton


        dreamListAdapter = DreamListAdapter(dreams, this, this)
        binding.dashboardRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.dashboardRecyclerView.adapter = dreamListAdapter

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("userID", loggedInUser.uid)
            startActivity(intent)
        }

        binding.seeAllDreamsBtn.setOnClickListener {
            val intent = Intent(this, DreamListViewActivity::class.java)
            intent.putExtra("userID", loggedInUser.uid)
            startActivity(intent)
        }

        fetchDreams()

        binding.toolbar.leftActionButton.setOnClickListener {
            val intent = Intent(this, AddNewDreamActivity::class.java)
            intent.putExtra("userID", loggedInUser.uid)
            startActivity(intent)
        }
        addButton.setOnClickListener {
            val intent = Intent(this, AddNewDreamActivity::class.java)
            intent.putExtra("userID", loggedInUser.uid)
            startActivity(intent)
        }
        binding.toolbar.logoutButton.setOnClickListener {
            logout()
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
        val intent = Intent(this, AddNewDreamActivity::class.java)
        intent.putExtra("dreamID", dream.dreamId)
        intent.putExtra("userID", loggedInUser.uid)
        startActivity(intent)
    }

    override fun onDeleteClick(dream: Dream) {
        TODO("Not yet implemented")
    }

    private fun fetchDreams() {
        database.collection("dreams")
            .whereEqualTo("userId", loggedInUser.uid)
            .orderBy("date", Query.Direction.DESCENDING) // Assuming 'date' is the field to sort by
            .limit(3)
            .get()
            .addOnSuccessListener { querySnapshot ->
                dreams.clear() // Clear existing items
                for (document in querySnapshot.documents) {
                    val dream = document.toObject(Dream::class.java)
                    dream!!.dreamId = document.id

                    dream?.let {
                        dreams.add(it) // Add dream to the list
                    }
                }
                Log.d("Dream ID RV", dreams.toString())
                binding.dashboardRecyclerView.adapter?.notifyDataSetChanged() // Notify the adapter
            }
            .addOnFailureListener { e ->
                displayErrorMessage(e.message ?: "Error fetching dreams", "FetchDreamsError")
            }
    }

    private fun displayErrorMessage(message: String, errorTag: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
        Log.e(errorTag, "Failed to fetch dreams. Response code: $message")
    }

    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}
