package com.example.itprogerapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.catalog.User

class Dbhelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                login TEXT,
                email TEXT,
                pass TEXT
            )
        """.trimIndent()
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues().apply {
            put("login", user.login)
            put("email", user.email)
            put("pass", user.pass)
        }

        val db = this.writableDatabase
        db.insert("users", null, values)
        db.close()
    }

    fun checkUser(login: String, pass: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE login = ? AND pass = ?"
        val cursor = db.rawQuery(query, arrayOf(login, pass))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun getUserByEmail(email: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        var user: User? = null
        if (cursor.moveToFirst()) {
            val login = cursor.getString(cursor.getColumnIndexOrThrow("login"))
            val pass = cursor.getString(cursor.getColumnIndexOrThrow("pass"))
            user = User(login, email, pass)
        }

        cursor.close()
        db.close()
        return user
    }

    fun updatePasswordByEmail(email: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("pass", newPassword)
        }
        val updatedRows = db.update("users", values, "email = ?", arrayOf(email))
        db.close()
        return updatedRows > 0
    }

    fun isLoginExists(login: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE login = ?", arrayOf(login))
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    fun isEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }
}
