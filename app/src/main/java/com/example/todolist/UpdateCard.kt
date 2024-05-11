package com.example.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.todolist.databinding.ActivityUpdateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCardBinding
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Room.databaseBuilder(
            applicationContext,myDatabase::class.java,"To_Do"
        ).build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            binding.createTitle.setText(title)
            binding.createPriority.setText(priority)

            binding.deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(pos+1,
                            binding.createTitle.text.toString(),
                            binding.createPriority.text.toString())
                    )
                }
                myIntent()
            }

            binding.updateButton.setOnClickListener {
                DataObject.updateData(
                    pos,
                    binding.createTitle.text.toString(),
                    binding.createPriority.text.toString()


                )
                GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(pos+1,binding.createTitle.text.toString(),
                            binding.createPriority.text.toString())
                    )

                }

                myIntent()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
