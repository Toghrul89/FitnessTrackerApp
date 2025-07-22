package com.example.it216groupproject1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WorkoutActivity : AppCompatActivity() {

    private var seconds = 0
    private var running = false
    private lateinit var handler: Handler

    private lateinit var timerTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        timerTextView = findViewById(R.id.tvTimer)
        caloriesTextView = findViewById(R.id.tvCalories)
        startButton = findViewById(R.id.btnStart)
        stopButton = findViewById(R.id.btnStop)

        handler = Handler(Looper.getMainLooper())
        runTimer()

        startButton.setOnClickListener {
            running = true
        }

        stopButton.setOnClickListener {
            running = false
        }
    }

    private fun runTimer() {
        handler.post(object : Runnable {
            override fun run() {
                if (running) {
                    seconds++
                }
                val minutes = seconds / 60
                val secs = seconds % 60
                timerTextView.text = String.format("Time: %02d:%02d", minutes, secs)

                // Estimate calories burned (simple logic: 0.12 cal/sec)
                val calories = seconds * 0.12
                caloriesTextView.text = String.format("Calories: %.1f", calories)

                handler.postDelayed(this, 1000)
            }
        })
    }
}
