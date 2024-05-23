package com.example.projetomob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityCustom : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        val dbController = DataBaseController(this)

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val todasPecas = dbController.getAllParts(this)

        val adapter = PartAdapter(todasPecas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}