package com.example.catalog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.itprogerapp.Dbhelper

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginField: EditText = findViewById(R.id.login_field)
        val passwordField: EditText = findViewById(R.id.password_field)
        val loginButton: Button = findViewById(R.id.button_login)
        val registerButton: Button = findViewById(R.id.button_register)

        loginButton.setOnClickListener {
            val login = loginField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show()
            } else {
                val db = Dbhelper(this, null)
                if (db.checkUser(login, password)) {
                    Toast.makeText(this, "Добро пожаловать, $login!", Toast.LENGTH_LONG).show()
                    // Здесь можно добавить переход на главный экран приложения
                } else {
                    Toast.makeText(this, "Неверные данные", Toast.LENGTH_LONG).show()
                }
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}