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
        database = AppDatabase.getDatabase(this)
        setContentView(binding.root)
        val loggedInUserID = intent.getIntExtra("userID",0)
        dreamListAdapter = DreamListAdapter(dreams,this,this)
        binding.dreamRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.dreamRecyclerView.adapter = dreamListAdapter
        GlobalScope.launch {
            currentUser = database.userDao().getUserById(loggedInUserID)!!
        }
        fetchDreams()
        binding.newDreamButton.setOnClickListener{
            val intent = Intent(this,AddNewDreamActivity::class.java)
            intent.putExtra("userID",loggedInUserID)
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
                dreams.reverse()
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