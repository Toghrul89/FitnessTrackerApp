package com.example.it216groupproject1.ui.goal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.it216groupproject1.data.model.FitnessGoal
import com.example.it216groupproject1.data.model.WorkoutType
import com.example.it216groupproject1.data.repository.FitnessRepository

class GoalViewModel(
    private val repository: FitnessRepository
) : ViewModel() {
    private val _goal = MutableLiveData<FitnessGoal>()
    val goal: LiveData<FitnessGoal> = _goal

    fun loadGoal(type: WorkoutType) {
        _goal.value = repository.getGoal(type)
    }

    fun saveGoal(type: WorkoutType?, goalDescription: String): Boolean {
        if (type == null) return false

        val trimmedGoal = goalDescription.trim()
        if (trimmedGoal.isEmpty()) return false

        val weeklyCalories = trimmedGoal.toIntOrNull() ?: repository.getGoal(type).weeklyCalories
        val goal = FitnessGoal(
            description = trimmedGoal,
            weeklyCalories = weeklyCalories
        )
        repository.saveGoal(type, goal)
        _goal.value = goal
        return true
    }
}
