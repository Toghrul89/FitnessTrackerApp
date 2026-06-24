package com.example.it216groupproject1.domain

import com.example.it216groupproject1.data.model.WorkoutSession
import com.example.it216groupproject1.data.model.WorkoutType

object FitnessCalculator {
    fun caloriesFor(type: WorkoutType, durationSeconds: Int): Double {
        return durationSeconds.coerceAtLeast(0) * type.caloriesPerSecond
    }

    fun averageDurationSeconds(sessions: List<WorkoutSession>): Int {
        if (sessions.isEmpty()) return 0
        return sessions.map { it.durationSeconds }.average().toInt()
    }

    fun totalCalories(sessions: List<WorkoutSession>): Int {
        return sessions.sumOf { it.caloriesBurned }.toInt()
    }
}
