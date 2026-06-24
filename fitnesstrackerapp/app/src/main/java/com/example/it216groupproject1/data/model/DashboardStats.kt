package com.example.it216groupproject1.data.model

data class DashboardStats(
    val weeklyGoalCalories: Int,
    val weeklyCaloriesBurned: Int,
    val totalWorkouts: Int,
    val averageDurationSeconds: Int
) {
    val progressPercent: Int
        get() = if (weeklyGoalCalories <= 0) {
            0
        } else {
            ((weeklyCaloriesBurned.toDouble() / weeklyGoalCalories) * 100).toInt()
        }
}
