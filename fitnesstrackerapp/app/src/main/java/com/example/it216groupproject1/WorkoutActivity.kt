package com.example.it216groupproject1

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.it216groupproject1.data.repository.FitnessRepository
import com.example.it216groupproject1.ui.common.FitnessViewModelFactory
import com.example.it216groupproject1.ui.common.configureWorkoutTypes
import com.example.it216groupproject1.ui.common.selectedWorkoutType
import com.example.it216groupproject1.ui.common.setupDashboardBackNavigation
import com.example.it216groupproject1.ui.workout.WorkoutViewModel

class WorkoutActivity : AppCompatActivity() {
    private lateinit var viewModel: WorkoutViewModel
    private lateinit var timerTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var workoutTypeSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        setupDashboardBackNavigation(R.id.toolbarWorkout)

        val repository = FitnessRepository.getInstance(applicationContext)
        val factory = FitnessViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[WorkoutViewModel::class.java]

        timerTextView = findViewById(R.id.tvTimer)
        caloriesTextView = findViewById(R.id.tvCalories)
        workoutTypeSpinner = findViewById(R.id.spinnerWorkoutType)
        workoutTypeSpinner.configureWorkoutTypes(this)

        viewModel.elapsedSeconds.observe(this) { seconds ->
            timerTextView.text = "Time: ${formatDuration(seconds)}"
        }
        viewModel.caloriesBurned.observe(this) { calories ->
            caloriesTextView.text = String.format("Calories: %.1f", calories)
        }

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            val workoutType = workoutTypeSpinner.selectedWorkoutType()
            if (workoutType == null) {
                Toast.makeText(this, "Please select a workout type.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.startWorkout(workoutType)
            }
        }

        findViewById<Button>(R.id.btnStop).setOnClickListener {
            viewModel.stopAndSaveWorkout()
            Toast.makeText(this, "Workout saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatDuration(totalSeconds: Int): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
