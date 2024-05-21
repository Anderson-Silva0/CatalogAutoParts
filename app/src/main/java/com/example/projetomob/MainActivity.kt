package com.example.projetomob

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetomob.ui.theme.ProjetoMOBTheme

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

