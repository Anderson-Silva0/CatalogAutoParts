package com.example.projetomob

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CreateDB(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    companion object {
        const val DB_NAME = "bancoautop.db"
        const val TABLE = "auto_parts"
        const val ID = "_id"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
        const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s BLOB)",
            TABLE, ID, DESCRIPTION, IMAGE
        )
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }
}