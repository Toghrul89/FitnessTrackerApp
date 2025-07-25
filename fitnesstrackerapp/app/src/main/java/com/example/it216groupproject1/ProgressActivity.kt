package com.example.it216groupproject1

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProgressActivity : AppCompatActivity() {

    private val PREFS_NAME = "FitnessPrefs"
    private val GOAL_KEY = "goal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val textViewGoal = findViewById<TextView>(R.id.textViewSavedGoal)

        // Load the saved goal
        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedGoal = sharedPrefs.getString(GOAL_KEY, null)

        // Show the goal or a message
        if (!savedGoal.isNullOrEmpty()) {
            textViewGoal.text = "Your current goal: $savedGoal"
        } else {
            textViewGoal.text = "No goal set yet. Go set one!"
        }
    }
}

