package com.example.projetomob

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.Image

class DataBaseController(context: Context) {

    private val database: CreateDB
    private val writableDatabase: SQLiteDatabase
    private val readableDatabase: SQLiteDatabase

    init{
        database = CreateDB(context)
        writableDatabase = database.writableDatabase
        readableDatabase = database.readableDatabase
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

}