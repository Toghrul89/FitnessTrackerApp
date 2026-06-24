package com.example.it216groupproject1.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.it216groupproject1.data.model.WorkoutSession
import com.example.it216groupproject1.data.repository.FitnessRepository

class HistoryViewModel(
    private val repository: FitnessRepository
) : ViewModel() {
    private val _sessions = MutableLiveData<List<WorkoutSession>>()
    val sessions: LiveData<List<WorkoutSession>> = _sessions

    fun loadHistory() {
        _sessions.value = repository.getWorkoutSessions()
    }
}
