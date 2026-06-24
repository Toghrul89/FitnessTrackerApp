package com.example.it216groupproject1.ui.workout

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.it216groupproject1.data.model.WorkoutSession
import com.example.it216groupproject1.data.model.WorkoutType
import com.example.it216groupproject1.data.repository.FitnessRepository
import com.example.it216groupproject1.domain.FitnessCalculator

class WorkoutViewModel(
    private val repository: FitnessRepository
) : ViewModel() {
    private val handler = Handler(Looper.getMainLooper())

    private val _elapsedSeconds = MutableLiveData(0)
    val elapsedSeconds: LiveData<Int> = _elapsedSeconds

    private val _caloriesBurned = MutableLiveData(0.0)
    val caloriesBurned: LiveData<Double> = _caloriesBurned

    private val _isRunning = MutableLiveData(false)
    val isRunning: LiveData<Boolean> = _isRunning

    private var workoutType = WorkoutType.RUNNING
    private var hasSavedCurrentWorkout = false

    private val timerRunnable = object : Runnable {
        override fun run() {
            if (_isRunning.value == true) {
                val nextSeconds = (_elapsedSeconds.value ?: 0) + 1
                _elapsedSeconds.value = nextSeconds
                _caloriesBurned.value = FitnessCalculator.caloriesFor(workoutType, nextSeconds)
                hasSavedCurrentWorkout = false
            }
            handler.postDelayed(this, ONE_SECOND_MILLIS)
        }
    }

    init {
        handler.post(timerRunnable)
    }

    fun startWorkout(type: WorkoutType = WorkoutType.RUNNING) {
        if (hasSavedCurrentWorkout) {
            _elapsedSeconds.value = 0
            _caloriesBurned.value = 0.0
            hasSavedCurrentWorkout = false
        }
        workoutType = type
        _isRunning.value = true
    }

    fun pauseWorkout() {
        _isRunning.value = false
    }

    fun stopAndSaveWorkout() {
        _isRunning.value = false
        val seconds = _elapsedSeconds.value ?: 0
        if (seconds <= 0 || hasSavedCurrentWorkout) return

        repository.saveWorkoutSession(
            WorkoutSession(
                id = System.currentTimeMillis(),
                type = workoutType,
                durationSeconds = seconds,
                caloriesBurned = _caloriesBurned.value ?: 0.0,
                completedAtMillis = System.currentTimeMillis()
            )
        )
        hasSavedCurrentWorkout = true
    }

    override fun onCleared() {
        handler.removeCallbacks(timerRunnable)
        super.onCleared()
    }

    companion object {
        private const val ONE_SECOND_MILLIS = 1000L
    }
}
