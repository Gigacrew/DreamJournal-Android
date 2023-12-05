package com.gigacrew.dreamjournal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigacrew.dreamjournal.databinding.ActivityDreamListViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DreamListViewActivity : AppCompatActivity(),
DreamListAdapter.OnItemClickListener,
DreamListAdapter.OnDeleteClickListener{

    private lateinit var binding: ActivityDreamListViewBinding
    private val dreams :ArrayList<Dream> = ArrayList()
    private lateinit var dreamListAdapter:DreamListAdapter
    private lateinit var database:FirebaseFirestore
    private lateinit var currentUser: FirebaseUser;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDreamListViewBinding.inflate(layoutInflater)
        database = FirebaseFirestore.getInstance()
        setContentView(binding.root)
        dreamListAdapter = DreamListAdapter(dreams,this,this)
        binding.dreamRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.dreamRecyclerView.adapter = dreamListAdapter
        currentUser = FirebaseAuth.getInstance().currentUser!!

        fetchDreams()
        binding.newDreamButton.setOnClickListener{
            val intent = Intent(this,AddNewDreamActivity::class.java)
            intent.putExtra("userID",currentUser.uid)
            startActivity(intent)
        }
        binding.toolbar.leftActionButton.setImageResource(R.drawable.ic_back)

        binding.toolbar.leftActionButton.setOnClickListener {
            finish()
        }
        binding.toolbar.toolbarTitle.setText("Dream List")
        binding.toolbar.logoutButton.setOnClickListener {
            logout()
        }
    }


    private fun fetchDreams() {
        database.collection("dreams")
            .whereEqualTo("userId", currentUser.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                dreams.clear() // Clear existing items
                for (document in querySnapshot.documents) {
                    val dream = document.toObject(Dream::class.java)
                    dream?.let {
                        dreams.add(it) // Add dream to the list
                    }
                }
                dreamListAdapter.notifyDataSetChanged() // Notify the adapter
            }
            .addOnFailureListener { e ->
                displayErrorMessage(e.message ?: "Error fetching dreams", "FetchDreamsError")
            }
    }

    override fun onItemClick(dream: Dream) {
        val intent = Intent(this,AddNewDreamActivity::class.java)
        intent.putExtra("dreamID", dream.dreamId)
        startActivity(intent)
    }

    override fun onDeleteClick(dream: Dream) {
        TODO("Not yet implemented")
    }

    private fun displayErrorMessage(message: String,errorTag : String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
        Log.e(errorTag,"Failed to fetch dreams. Response code: $message" )
    }
    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}