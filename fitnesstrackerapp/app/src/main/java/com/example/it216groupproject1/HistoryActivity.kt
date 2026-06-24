package com.example.it216groupproject1

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.it216groupproject1.data.model.WorkoutSession
import com.example.it216groupproject1.data.repository.FitnessRepository
import com.example.it216groupproject1.ui.common.FitnessViewModelFactory
import com.example.it216groupproject1.ui.common.setupDashboardBackNavigation
import com.example.it216groupproject1.ui.history.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryActivity : AppCompatActivity() {
    private lateinit var viewModel: HistoryViewModel
    private lateinit var historyList: LinearLayout
    private lateinit var emptyStateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setupDashboardBackNavigation(R.id.toolbarHistory)

        val repository = FitnessRepository.getInstance(applicationContext)
        val factory = FitnessViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        historyList = findViewById(R.id.layoutHistoryList)
        emptyStateTextView = findViewById(R.id.tvHistoryEmpty)

        viewModel.sessions.observe(this) { sessions ->
            renderHistory(sessions)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistory()
    }

    private fun renderHistory(sessions: List<WorkoutSession>) {
        historyList.removeAllViews()
        emptyStateTextView.visibility = if (sessions.isEmpty()) View.VISIBLE else View.GONE

        sessions.forEach { session ->
            historyList.addView(createHistoryRow(session))
        }
    }

    private fun createHistoryRow(session: WorkoutSession): View {
        val row = layoutInflater.inflate(R.layout.item_workout_history, historyList, false)
        row.findViewById<TextView>(R.id.tvHistoryType).text = session.type.displayName
        row.findViewById<TextView>(R.id.tvHistoryDate).text = formatDate(session.completedAtMillis)
        row.findViewById<TextView>(R.id.tvHistoryDuration).text = formatDuration(session.durationSeconds)
        row.findViewById<TextView>(R.id.tvHistoryCalories).text = "${session.caloriesBurned.toInt()} kcal"
        return row
    }

    private fun formatDuration(totalSeconds: Int): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.US, "%02d:%02d", minutes, seconds)
    }

    private fun formatDate(millis: Long): String {
        return SimpleDateFormat("MMM d, yyyy - h:mm a", Locale.US).format(Date(millis))
    }
}
