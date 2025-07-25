package com.example.it216groupproject1

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SetGoalActivity : AppCompatActivity() {

    // Constants for preferences
    private val PREFS_NAME = "FitnessPrefs"
    private val GOAL_KEY = "goal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)

        val goalInput = findViewById<EditText>(R.id.editTextGoal)
        val saveButton = findViewById<Button>(R.id.buttonSaveGoal)

        // Load saved goal if it exists
        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedGoal = sharedPrefs.getString(GOAL_KEY, "")
        goalInput.setText(savedGoal)

        // Save goal when button is clicked
        saveButton.setOnClickListener {
            val goalText = goalInput.text.toString().trim()
            if (goalText.isNotEmpty()) {
                val editor = sharedPrefs.edit()
                editor.putString(GOAL_KEY, goalText)
                editor.apply()
                Toast.makeText(this, "Goal saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a goal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
