package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.todolist.databinding.ActivityCreateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCard : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCardBinding
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Room.databaseBuilder(
            applicationContext,myDatabase::class.java,"To_Do"
        ).build()

        binding.saveButton.setOnClickListener {
            val title = binding.createTitle.text.toString().trim()
            val priority = binding.createPriority.text.toString().trim()

            if (title.isNotEmpty() && priority.isNotEmpty()) {
                DataObject.setData(title, priority)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0,title,priority))

                }
                GlobalScope.launch{
                    Log.i("database",database.dao().getTasks().toString())
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
