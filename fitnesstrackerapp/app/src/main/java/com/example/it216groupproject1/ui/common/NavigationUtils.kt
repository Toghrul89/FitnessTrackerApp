package com.example.it216groupproject1.ui.common

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.it216groupproject1.DashboardActivity
import com.google.android.material.appbar.MaterialToolbar

fun AppCompatActivity.setupDashboardBackNavigation(toolbarId: Int) {
    findViewById<MaterialToolbar>(toolbarId).setNavigationOnClickListener {
        navigateToDashboard()
    }

    onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToDashboard()
            }
        }
    )
}

private fun AppCompatActivity.navigateToDashboard() {
    val intent = Intent(this, DashboardActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    }
    startActivity(intent)
    finish()
}
