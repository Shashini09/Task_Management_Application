package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.example.todolist.databinding.ActivitySplashScreenBinding // Import ViewBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= Room.databaseBuilder(
            applicationContext,myDatabase::class.java,"To_Do"
        ).build()

        GlobalScope.launch{
           DataObject.listdata= database.dao().getTasks() as MutableList<CardInfo>
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
