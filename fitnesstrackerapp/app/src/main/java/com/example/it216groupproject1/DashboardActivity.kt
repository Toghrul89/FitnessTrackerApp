package com.example.it216groupproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.it216groupproject1.data.model.DashboardStats
import com.example.it216groupproject1.data.repository.FitnessRepository
import com.example.it216groupproject1.ui.common.FitnessViewModelFactory
import com.example.it216groupproject1.ui.dashboard.DashboardViewModel

open class DashboardActivity : AppCompatActivity() {
    private lateinit var viewModel: DashboardViewModel
    private lateinit var goalTextView: TextView
    private lateinit var progressGoal: ProgressBar
    private lateinit var progressPercentTextView: TextView
    private lateinit var caloriesSummaryTextView: TextView
    private lateinit var workoutSummaryTextView: TextView
    private lateinit var averageSummaryTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = FitnessRepository.getInstance(applicationContext)
        val factory = FitnessViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]

        goalTextView = findViewById(R.id.tvUserGoal)
        progressGoal = findViewById(R.id.progressGoal)
        progressPercentTextView = findViewById(R.id.tvProgressPercent)
        caloriesSummaryTextView = findViewById(R.id.tvCaloriesSummary)
        workoutSummaryTextView = findViewById(R.id.tvWorkoutSummary)
        averageSummaryTextView = findViewById(R.id.tvAverageSummary)

        viewModel.goal.observe(this) { goal ->
            goalTextView.text = "Your Goal: ${goal.description}"
        }
        viewModel.stats.observe(this) { stats ->
            bindStats(stats)
        }

        findViewById<Button>(R.id.btnStartWorkout).setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }

        findViewById<Button>(R.id.btnSetGoal).setOnClickListener {
            startActivity(Intent(this, SetGoalActivity::class.java))
        }

        findViewById<Button>(R.id.btnViewProgress).setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        findViewById<Button>(R.id.btnViewHistory).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDashboard()
    }

    private fun bindStats(stats: DashboardStats) {
        val percent = stats.progressPercent.coerceIn(0, 100)
        progressGoal.progress = percent
        progressPercentTextView.text = "$percent%"
        caloriesSummaryTextView.text = "${stats.weeklyCaloriesBurned} kcal"
        workoutSummaryTextView.text = stats.totalWorkouts.toString()
        averageSummaryTextView.text = "${stats.averageDurationSeconds / 60} min"
    }
}
