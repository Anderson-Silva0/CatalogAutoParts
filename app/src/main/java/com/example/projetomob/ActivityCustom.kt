package com.example.projetomob

import android.os.Bundle
import androidx.activity.ComponentActivity

class ActivityCustom : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)
    }
}