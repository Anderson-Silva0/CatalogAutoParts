package com.example.projetomob

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import java.io.ByteArrayOutputStream

class DataBaseController(context: Context) {

    private val database: CreateDB
    private val writableDatabase: SQLiteDatabase
    private val readableDatabase: SQLiteDatabase

    init {
        database = CreateDB(context)
        writableDatabase = database.writableDatabase
        readableDatabase = database.readableDatabase
    }

    fun salvarImagemEDescricao(bitmap: Bitmap, descricao: String, context: Context) {
        val db = writableDatabase
        try {
            val contentValues = ContentValues().apply {
                put(CreateDB.DESCRIPTION, descricao)
                put(CreateDB.IMAGE, getBytesFromBitmap(bitmap))
            }

            val newRowId = db.insert(CreateDB.TABLE, null, contentValues)
            if (newRowId == -1L) {
                Toast.makeText(context, "Erro ao tentar salvar imagem", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Imagem Salva com sucesso id: $newRowId", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("DataBaseController", "Error saving image and description", e)
        } finally {
            db.close()
        }
    }

    fun updateImagemEDescricao(id: Int, bitmap: Bitmap, descricao: String, context: Context) {
        val db = writableDatabase
        try {
            val contentValues = ContentValues().apply {
                put(CreateDB.DESCRIPTION, descricao)
                put(CreateDB.IMAGE, getBytesFromBitmap(bitmap))
            }

            val selection = "${CreateDB.ID} = ?"
            val selectionArgs = arrayOf(id.toString())

            val count = db.update(CreateDB.TABLE, contentValues, selection, selectionArgs)
            if (count == 0) {
                Toast.makeText(context, "Erro ao tentar atualizar imagem", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Imagem atualizada com sucesso", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("DataBaseController", "Error updating image and description", e)
        } finally {
            db.close()
        }
    }

    fun getAllParts(context: Context): ArrayList<Part> {
        val partsList = ArrayList<Part>()
        val db = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query(
                CreateDB.TABLE,
                arrayOf(CreateDB.ID, CreateDB.IMAGE, CreateDB.DESCRIPTION),
                null, null, null, null, null
            )

            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(CreateDB.ID))
                    val image = getBlob(getColumnIndexOrThrow(CreateDB.IMAGE))
                    val description = getString(getColumnIndexOrThrow(CreateDB.DESCRIPTION))
                    partsList.add(Part(id, image, description))
                }
            }

            Toast.makeText(context, "Dados carregados com sucesso", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Erro ao carregar dados: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            cursor?.close()
            db.close()
        }
        return partsList
    }

    fun deleteById(id: Int) {
        val where = "${CreateDB.ID} = ?"
        val whereArgs = arrayOf(id.toString())
        writableDatabase.delete(CreateDB.TABLE, where, whereArgs)
    }
    fun deleteAll() {
        writableDatabase.delete(CreateDB.TABLE, null, null)
    }

    private fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

}