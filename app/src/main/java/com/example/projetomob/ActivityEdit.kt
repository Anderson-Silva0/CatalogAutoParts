package com.example.projetomob

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity

class ActivityEdit : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val id = intent.getIntExtra("TAG_ID", -1)
        val imageByte = intent.getByteArrayExtra("TAG_IMAGE")
        val description = intent.getStringExtra("TAG_DESCRIPTION")

        val bitmapImage = imageByte?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }

        val etUpdate = findViewById<EditText>(R.id.et_update)
        val imgViewEdit = findViewById<ImageView>(R.id.imageViewEdit)

        imgViewEdit.setImageBitmap(bitmapImage)
        etUpdate.setText(description)


    }
}