package com.example.it216groupproject1.data.model

data class FitnessGoal(
    val description: String,
    val weeklyCalories: Int
) {
    companion object {
        const val DEFAULT_DESCRIPTION = "No goal set yet."
        const val DEFAULT_WEEKLY_CALORIES = 300
    }
}
