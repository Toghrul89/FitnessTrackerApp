package com.example.it216groupproject1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.it216groupproject1.data.model.FitnessGoal
import com.example.it216groupproject1.data.repository.FitnessRepository
import com.example.it216groupproject1.ui.common.FitnessViewModelFactory
import com.example.it216groupproject1.ui.common.configureWorkoutTypes
import com.example.it216groupproject1.ui.common.selectedWorkoutType
import com.example.it216groupproject1.ui.common.setupDashboardBackNavigation
import com.example.it216groupproject1.ui.goal.GoalViewModel

class SetGoalActivity : AppCompatActivity() {
    private lateinit var viewModel: GoalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)
        setupDashboardBackNavigation(R.id.toolbarSetGoal)

        val repository = FitnessRepository.getInstance(applicationContext)
        val factory = FitnessViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[GoalViewModel::class.java]

        val goalInput = findViewById<EditText>(R.id.editTextGoal)
        val saveButton = findViewById<Button>(R.id.buttonSaveGoal)
        val workoutTypeSpinner = findViewById<Spinner>(R.id.spinnerGoalWorkoutType)
        workoutTypeSpinner.configureWorkoutTypes(this)

        viewModel.goal.observe(this) { goal ->
            if (goal.description != FitnessGoal.DEFAULT_DESCRIPTION) {
                goalInput.setText(goal.description)
            } else {
                goalInput.setText("")
            }
        }

        workoutTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                workoutTypeSpinner.selectedWorkoutType()?.let { type ->
                    viewModel.loadGoal(type)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        saveButton.setOnClickListener {
            val selectedWorkoutType = workoutTypeSpinner.selectedWorkoutType()
            if (selectedWorkoutType == null) {
                Toast.makeText(this, "Please select a workout type.", Toast.LENGTH_SHORT).show()
            } else if (viewModel.saveGoal(selectedWorkoutType, goalInput.text.toString())) {
                Toast.makeText(this, "Goal saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a goal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
