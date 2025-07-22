package com.example.it216groupproject1

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SetGoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)

        val goalInput = findViewById<EditText>(R.id.etGoal)
        val saveButton = findViewById<Button>(R.id.btnSaveGoal)

        val sharedPrefs = getSharedPreferences("FitnessPrefs", Context.MODE_PRIVATE)

        saveButton.setOnClickListener {
            val goalText = goalInput.text.toString().trim()

            if (goalText.isNotEmpty()) {
                with(sharedPrefs.edit()) {
                    putString("user_goal", goalText)
                    apply()
                }
                Toast.makeText(this, "Goal saved!", Toast.LENGTH_SHORT).show()
                finish() // return to MainActivity
            } else {
                Toast.makeText(this, "Please enter a goal.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
