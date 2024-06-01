package com.example.projetomob

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity

@Suppress("DEPRECATION")
class ActivityCreate : ComponentActivity() {

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val btGaleria: Button = findViewById<Button>(R.id.btGaleria)
        val imagemGaleria: ImageView = findViewById<ImageView>(R.id.imageViewGaleria)
        val descricaoText: EditText = findViewById<EditText>(R.id.descricaoText)
        val btSalvarImagem: Button = findViewById<Button>(R.id.btSalvarImagem)

        btGaleria.setOnClickListener {
            this.selectImageInAlbum()
        }

        btSalvarImagem.setOnClickListener {
            val dbController = DataBaseController(this)
            val drawable = imagemGaleria.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                val compressedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)

                try {
                    dbController.salvarImagemEDescricao(compressedBitmap, descricaoText.text.toString(), this)
                } catch (e: Exception) {
                    Log.e("ActivityCreate", "Error saving image and description", e)
                }

                imagemGaleria.setImageURI(null)
                imagemGaleria.setImageDrawable(null)
                descricaoText.setText("")
            }
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    data?.data?.let { uri ->
                        val imageView: ImageView = findViewById(R.id.imageViewGaleria)
                        imageView.setImageURI(uri)
                    }
                }
                REQUEST_TAKE_PHOTO -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let { bitmap ->
                        val imageView: ImageView = findViewById(R.id.imageViewGaleria)
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

}