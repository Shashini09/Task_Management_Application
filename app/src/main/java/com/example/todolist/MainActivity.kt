package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todolist.databinding.ActivityMainBinding // Import ViewBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//entity-table
//dao-queries
//database

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Room.databaseBuilder(
            applicationContext,myDatabase::class.java,"To_Do"
        ).build()

        binding.add.setOnClickListener{
            val intent= Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        binding.deleteAll.setOnClickListener{
            DataObject.deleteAll()
            GlobalScope.launch{
                database.dao().deleteAll()
            }
            setRecycler()
        }

        setRecycler()


    }
    fun setRecycler(){
        binding.recyclerView.adapter = Adapter(DataObject.getAllData())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }
}
