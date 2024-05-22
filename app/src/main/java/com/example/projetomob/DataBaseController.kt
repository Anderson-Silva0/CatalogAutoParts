package com.example.projetomob

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
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

        val contentValues = ContentValues().apply {
            put(CreateDB.IMAGE, getBytesFromBitmap(bitmap))
        }

        val newRowId = db.insert(CreateDB.TABLE, descricao, contentValues)
        if (newRowId == -1L) {
            Toast.makeText(context, "Erro ao tentar salvar imagem", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Imagem Salva com sucesso id: $newRowId", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

//    fun insertData(description: String, image: Image): String {
//        val values = ContentValues()
//        values.put(CreateDB.DESCRIPTION, description)
//        values.put(CreateDB.IMAGE, image)
//        val result = writableDatabase.insert(CreateDB.TABLE,null, values)
//        return if (result == -1L) "Error inserting record" else "Record inserting successfully"
//    }

//    fun loadData(): Cursor?{
//        val fields = arrayOf(CreateDB.ID, CreateDB.DESCRIPTION)
//        val cursor = readableDatabase.query(CreateDB.TABLE, fields, null, null,null)
//        cursor.moveToFirst()
//        return cursor
//    }

    fun loadDataById(id: Int): Cursor?{
        val fields = arrayOf(CreateDB.ID, CreateDB.DESCRIPTION, CreateDB.IMAGE)
        val where = "${CreateDB.ID} = ?"
        val whereArgs = arrayOf(id.toString())
        val cursor = readableDatabase.query(CreateDB.TABLE, fields, where, whereArgs, null, null, null)
        cursor.moveToFirst()
        return cursor
    }

//    fun updateData(id: Int, description: String, image: Image){
//        val values = ContentValues()
//        values.put(CreateDB.DESCRIPTION, description)
//        values.put(CreateDB.IMAGE, image)
//        val where = "${CreateDB.ID} = ?"
//        val whereArgs = arrayOf(id.toString())
//        writableDatabase.update(CreateDB.TABLE, values, where, whereArgs)
//    }

    fun deleteData(id: Int){
        val where = "${CreateDB.ID} = ?"
        val whereArgs = arrayOf(id.toString())
        writableDatabase.delete(CreateDB.TABLE, where, whereArgs)
    }

    private fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

}