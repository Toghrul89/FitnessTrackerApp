package com.example.it216groupproject1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.it216groupproject1.data.model.DashboardStats
import com.example.it216groupproject1.data.model.FitnessGoal
import com.example.it216groupproject1.data.repository.FitnessRepository
import com.example.it216groupproject1.ui.common.FitnessViewModelFactory
import com.example.it216groupproject1.ui.common.configureWorkoutTypes
import com.example.it216groupproject1.ui.common.selectedWorkoutType
import com.example.it216groupproject1.ui.common.setupDashboardBackNavigation
import com.example.it216groupproject1.ui.progress.ProgressViewModel

class ProgressActivity : AppCompatActivity() {
    private lateinit var viewModel: ProgressViewModel
    private lateinit var progressWeeklyCalories: ProgressBar
    private lateinit var progressSummaryTextView: TextView
    private lateinit var totalWorkoutsTextView: TextView
    private lateinit var averageDurationTextView: TextView
    private lateinit var selectedWorkoutTypeTextView: TextView
    private lateinit var progressWorkoutTypeSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        setupDashboardBackNavigation(R.id.toolbarProgress)

        val repository = FitnessRepository.getInstance(applicationContext)
        val factory = FitnessViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ProgressViewModel::class.java]

        val textViewGoal = findViewById<TextView>(R.id.textViewSavedGoal)
        progressWeeklyCalories = findViewById(R.id.progressWeeklyCalories)
        progressSummaryTextView = findViewById(R.id.tvPlaceholder)
        totalWorkoutsTextView = findViewById(R.id.tvTotalWorkouts)
        averageDurationTextView = findViewById(R.id.tvAverageDuration)
        selectedWorkoutTypeTextView = findViewById(R.id.tvSelectedWorkoutType)
        progressWorkoutTypeSpinner = findViewById(R.id.spinnerProgressWorkoutType)
        progressWorkoutTypeSpinner.configureWorkoutTypes(this)

        viewModel.goal.observe(this) { goal ->
            textViewGoal.text = goal.toDisplayText()
        }
        viewModel.stats.observe(this) { stats ->
            bindStats(stats)
        }

        progressWorkoutTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                loadSelectedProgress()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    override fun onResume() {
        super.onResume()
        loadSelectedProgress()
    }

    private fun FitnessGoal.toDisplayText(): String {
        return if (description.isNotEmpty() && description != FitnessGoal.DEFAULT_DESCRIPTION) {
            "Your current goal: $description"
        } else {
            "No goal set yet. Go set one!"
        }
    }

    private fun bindStats(stats: DashboardStats) {
        val percent = stats.progressPercent.coerceIn(0, 100)
        progressWeeklyCalories.progress = percent
        progressSummaryTextView.text = "${stats.weeklyCaloriesBurned} / ${stats.weeklyGoalCalories} kcal     $percent%"
        totalWorkoutsTextView.text = stats.totalWorkouts.toString()
        averageDurationTextView.text = "${stats.averageDurationSeconds / 60} min"
    }

    private fun loadSelectedProgress() {
        val selectedType = progressWorkoutTypeSpinner.selectedWorkoutType()
        selectedWorkoutTypeTextView.text = if (selectedType == null) {
            "Selected workout type: All Workouts"
        } else {
            "Selected workout type: ${selectedType.displayName}"
        }
        viewModel.loadProgress(selectedType)
    }
}
