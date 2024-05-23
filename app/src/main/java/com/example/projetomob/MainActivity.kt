package com.example.projetomob

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btCreate: Button = findViewById<Button>(R.id.btCreate)
        val btCustom: Button = findViewById<Button>(R.id.btCustom)

        btCreate.setOnClickListener{
            startNewActivity(ActivityCreate::class.java)
        }

        btCustom.setOnClickListener{
            startNewActivity(ActivityCustom::class.java)
        }
    }

    private fun startNewActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}

