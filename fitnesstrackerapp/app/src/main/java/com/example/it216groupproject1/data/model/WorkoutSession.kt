package com.example.it216groupproject1.data.model

data class WorkoutSession(
    val id: Long,
    val type: WorkoutType,
    val durationSeconds: Int,
    val caloriesBurned: Double,
    val completedAtMillis: Long
)
