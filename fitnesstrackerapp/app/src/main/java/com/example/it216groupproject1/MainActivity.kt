package com.example.it216groupproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var enterName: EditText
    private lateinit var setGoalButton: Button
    private lateinit var trackActivitiesButton: Button
    private lateinit var viewProgressButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterName = findViewById(R.id.enterName)
        setGoalButton = findViewById(R.id.setGoalButton)
        trackActivitiesButton = findViewById(R.id.trackActivitiesButton)
        viewProgressButton = findViewById(R.id.viewProgressButton)

        setGoalButton.setOnClickListener {
            val intent = Intent(this, SetGoalActivity::class.java)
            startActivity(intent)
        }

        trackActivitiesButton.setOnClickListener {
            val intent = Intent(this, TrackActivitiesActivity::class.java)
            startActivity(intent)
        }

        viewProgressButton.setOnClickListener {
            val intent = Intent(this, ViewProgressActivity::class.java)
            startActivity(intent)
        }
    }
}
