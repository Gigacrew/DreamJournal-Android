package com.gigacrew.dreamjournal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigacrew.dreamjournal.databinding.ActivityDreamListViewBinding
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
    private lateinit var database:AppDatabase
    private lateinit var currentUser:User;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDreamListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dreamListAdapter = DreamListAdapter(dreams,this,this)
        binding.dreamRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.dreamRecyclerView.adapter = dreamListAdapter
        currentUser = User(0,"edonn","Eric","Donnelly","eric@example.com","password","9051234567")
        // TODO: current user should be set by taking the user from the login field
        database = AppDatabase.getDatabase(this)
        fetchDreams()
        binding.TEMPbutton.setOnClickListener{
            val intent = Intent(this,AddNewDreamActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchDreams(){
        GlobalScope.launch (Dispatchers.Main){
            try {
                val response = withContext(Dispatchers.IO){
                    database.dreamDAO().getAllDreamsForUser(currentUser.user_id) //
                }
                dreams.clear()
                dreams.addAll(response)
                dreamListAdapter.notifyDataSetChanged()
            }catch (e:Exception){
                displayErrorMessage("Failed to fetch dreams. Error: ${e.message}","Fetch Error")
            }
        }
    }

    override fun onItemClick(dream: Dream) {
        val intent = Intent(this,AddNewDreamActivity::class.java)
        intent.putExtra("dreamID", dream.dream_id)
        startActivity(intent)
    }

    override fun onDeleteClick(dream: Dream) {
        TODO("Not yet implemented")
    }

    private fun displayErrorMessage(message: String,errorTag : String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
        Log.e(errorTag,"Failed to fetch dreams. Response code: $message" )
    }

}