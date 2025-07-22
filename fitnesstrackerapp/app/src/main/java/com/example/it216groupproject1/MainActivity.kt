package com.example.it216groupproject1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE)
        val savedGoal = sharedPrefs.getString("user_goal", "No goal set yet.")

        val goalTextView = findViewById<TextView>(R.id.tvUserGoal)
        goalTextView.text = "Your Goal: $savedGoal"

        findViewById<Button>(R.id.btnStartWorkout).setOnClickListener {
            val intent = Intent(this, WorkoutActivity::class.java)
            startActivity(intent)

        }

        findViewById<Button>(R.id.btnSetGoal).setOnClickListener {
            val intent = Intent(this, SetGoalActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnViewProgress).setOnClickListener {
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent)
        }
    }
}