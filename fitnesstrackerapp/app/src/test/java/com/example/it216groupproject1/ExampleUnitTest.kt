package com.example.it216groupproject1

import com.example.it216groupproject1.data.model.WorkoutSession
import com.example.it216groupproject1.data.model.WorkoutType
import com.example.it216groupproject1.domain.FitnessCalculator
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun caloriesForRunningUsesWorkoutRate() {
        val calories = FitnessCalculator.caloriesFor(WorkoutType.RUNNING, durationSeconds = 60)

        assertEquals(9.6, calories, 0.01)
    }

    @Test
    fun averageDurationReturnsZeroForEmptyHistory() {
        val averageDuration = FitnessCalculator.averageDurationSeconds(emptyList())

        assertEquals(0, averageDuration)
    }

    @Test
    fun totalCaloriesSumsCompletedSessions() {
        val sessions = listOf(
            WorkoutSession(
                id = 1L,
                type = WorkoutType.RUNNING,
                durationSeconds = 60,
                caloriesBurned = 9.6,
                completedAtMillis = 1L
            ),
            WorkoutSession(
                id = 2L,
                type = WorkoutType.YOGA,
                durationSeconds = 60,
                caloriesBurned = 4.8,
                completedAtMillis = 2L
            )
        )

        assertEquals(14, FitnessCalculator.totalCalories(sessions))
    }
}
