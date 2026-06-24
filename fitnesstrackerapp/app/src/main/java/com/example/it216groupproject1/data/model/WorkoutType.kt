package com.example.it216groupproject1.data.model

enum class WorkoutType(
    val displayName: String,
    val description: String,
    val caloriesPerSecond: Double
) {
    RUNNING("Running", "Outdoor running", 0.16),
    HIKING("Hiking", "Outdoor trail activity", 0.14),
    STRENGTH_TRAINING("Strength Training", "Weight training", 0.11),
    CORE_TRAINING("Core Training", "Core strength session", 0.10),
    YOGA("Yoga", "Mobility and recovery", 0.06);

    companion object {
        const val SELECT_PROMPT = "Select Workout Type"

        fun fromName(name: String): WorkoutType {
            return entries.firstOrNull { it.name == name } ?: RUNNING
        }

        fun fromDisplayName(displayName: String): WorkoutType? {
            return entries.firstOrNull { it.displayName == displayName }
        }

        fun dropdownLabels(): List<String> {
            return listOf(SELECT_PROMPT) + entries.map { it.displayName }
        }
    }
}
