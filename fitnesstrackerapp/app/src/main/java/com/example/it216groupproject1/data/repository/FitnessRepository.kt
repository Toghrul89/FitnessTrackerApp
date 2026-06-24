package com.example.it216groupproject1.data.repository

import android.content.Context
import com.example.it216groupproject1.data.model.DashboardStats
import com.example.it216groupproject1.data.model.FitnessGoal
import com.example.it216groupproject1.data.model.WorkoutSession
import com.example.it216groupproject1.data.model.WorkoutType
import com.example.it216groupproject1.domain.FitnessCalculator

class FitnessRepository private constructor(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getGoal(): FitnessGoal {
        return getGoal(WorkoutType.RUNNING)
    }

    fun getGoal(type: WorkoutType): FitnessGoal {
        val description = preferences.getString(
            goalDescriptionKey(type),
            preferences.getString(
                KEY_LEGACY_GOAL,
                preferences.getString(KEY_LEGACY_USER_GOAL, FitnessGoal.DEFAULT_DESCRIPTION)
            )
        ) ?: FitnessGoal.DEFAULT_DESCRIPTION
        val weeklyCalories = preferences.getInt(goalCaloriesKey(type), FitnessGoal.DEFAULT_WEEKLY_CALORIES)
        return FitnessGoal(description, weeklyCalories)
    }

    fun saveGoal(goal: FitnessGoal) {
        saveGoal(WorkoutType.RUNNING, goal)
    }

    fun saveGoal(type: WorkoutType, goal: FitnessGoal) {
        preferences.edit()
            .putInt(goalCaloriesKey(type), goal.weeklyCalories)
            .putString(goalDescriptionKey(type), goal.description)
            .putString(KEY_LEGACY_GOAL, goal.description)
            .putString(KEY_LEGACY_USER_GOAL, goal.description)
            .apply()
    }

    fun getWorkoutSessions(): List<WorkoutSession> {
        val storedSessions = preferences.getStringSet(KEY_WORKOUT_SESSIONS, emptySet()).orEmpty()
        return storedSessions
            .mapNotNull { session -> session.toWorkoutSessionOrNull() }
            .sortedByDescending { it.completedAtMillis }
    }

    fun saveWorkoutSession(session: WorkoutSession) {
        val updatedSessions = getWorkoutSessions()
            .plus(session)
            .map { it.toPreferenceString() }
            .toSet()

        preferences.edit()
            .putStringSet(KEY_WORKOUT_SESSIONS, updatedSessions)
            .apply()
    }

    fun getDashboardStats(): DashboardStats {
        return getDashboardStats(null)
    }

    fun getDashboardStats(type: WorkoutType?): DashboardStats {
        val sessions = getWorkoutSessions()
            .filter { session -> type == null || session.type == type }
        return DashboardStats(
            weeklyGoalCalories = (type?.let { getGoal(it) } ?: getGoal()).weeklyCalories,
            weeklyCaloriesBurned = FitnessCalculator.totalCalories(sessions),
            totalWorkouts = sessions.size,
            averageDurationSeconds = FitnessCalculator.averageDurationSeconds(sessions)
        )
    }

    private fun WorkoutSession.toPreferenceString(): String {
        return listOf(
            id,
            type.name,
            durationSeconds,
            caloriesBurned,
            completedAtMillis
        ).joinToString(SESSION_SEPARATOR)
    }

    private fun String.toWorkoutSessionOrNull(): WorkoutSession? {
        val parts = split(SESSION_SEPARATOR)
        if (parts.size != SESSION_FIELD_COUNT) return null

        return runCatching {
            WorkoutSession(
                id = parts[0].toLong(),
                type = WorkoutType.fromName(parts[1]),
                durationSeconds = parts[2].toInt(),
                caloriesBurned = parts[3].toDouble(),
                completedAtMillis = parts[4].toLong()
            )
        }.getOrNull()
    }

    private fun goalDescriptionKey(type: WorkoutType): String {
        return "${KEY_GOAL_DESCRIPTION_PREFIX}${type.name}"
    }

    private fun goalCaloriesKey(type: WorkoutType): String {
        return "${KEY_WEEKLY_CALORIE_GOAL_PREFIX}${type.name}"
    }

    companion object {
        private const val PREFS_NAME = "FitnessPrefs"
        private const val KEY_WEEKLY_CALORIE_GOAL_PREFIX = "weekly_calorie_goal_"
        private const val KEY_GOAL_DESCRIPTION_PREFIX = "goal_description_"
        private const val KEY_WORKOUT_SESSIONS = "workout_sessions"
        private const val KEY_LEGACY_GOAL = "goal"
        private const val KEY_LEGACY_USER_GOAL = "user_goal"
        private const val SESSION_SEPARATOR = "|"
        private const val SESSION_FIELD_COUNT = 5

        @Volatile
        private var instance: FitnessRepository? = null

        fun getInstance(context: Context): FitnessRepository {
            return instance ?: synchronized(this) {
                instance ?: FitnessRepository(context).also { instance = it }
            }
        }
    }
}
