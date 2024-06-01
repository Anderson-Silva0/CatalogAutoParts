package com.example.projetomob

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity

class ActivityEdit : ComponentActivity() {

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val dbController = DataBaseController(this)
        val btGaleria = findViewById<Button>(R.id.btImage)
        val btAtualizar = findViewById<Button>(R.id.btUpdate)

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

        btGaleria.setOnClickListener {
            this.selectImageInAlbum()
        }

        btAtualizar.setOnClickListener {
            val drawable = imgViewEdit.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                val compressedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)

                try {
                    dbController.updateImagemEDescricao(id, compressedBitmap, etUpdate.text.toString(), this)
                } catch (e: Exception) {
                    Log.e("ActivityCreate", "Error saving image and description", e)
                }

                imgViewEdit.setImageURI(null)
                imgViewEdit.setImageDrawable(null)
                etUpdate.setText("")

                val intent = Intent(this, ActivityCustom::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, ActivityEdit.REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ActivityEdit.REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    data?.data?.let { uri ->
                        val imageView: ImageView = findViewById(R.id.imageViewEdit)
                        imageView.setImageURI(uri)
                    }
                }
                ActivityEdit.REQUEST_TAKE_PHOTO -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let { bitmap ->
                        val imageView: ImageView = findViewById(R.id.imageViewEdit)
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}