package com.example.it216groupproject1.ui.common

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.it216groupproject1.R
import com.example.it216groupproject1.data.model.WorkoutType

fun Spinner.configureWorkoutTypes(context: Context) {
    val workoutTypeAdapter = ArrayAdapter(
        context,
        R.layout.item_spinner_workout_type,
        WorkoutType.dropdownLabels()
    )
    workoutTypeAdapter.setDropDownViewResource(R.layout.item_spinner_workout_type_dropdown)
    adapter = workoutTypeAdapter
}

fun Spinner.selectedWorkoutType(): WorkoutType? {
    return WorkoutType.fromDisplayName(selectedItem?.toString().orEmpty())
}
