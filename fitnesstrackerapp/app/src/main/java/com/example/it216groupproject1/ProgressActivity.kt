package com.example.it216groupproject1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish() // return to MainActivity
        }
    }
}
