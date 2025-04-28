package com.example.catalog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.itprogerapp.Dbhelper

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
        if (sharedPref.getBoolean("isLoggedIn", false)) {
            val login = sharedPref.getString("login", "")!!
            startActivity(Intent(this, ProductListActivity::class.java).putExtra("USER_LOGIN", login))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val loginField: EditText = findViewById(R.id.login_field)
        val passwordField: EditText = findViewById(R.id.password_field)
        val loginButton: Button = findViewById(R.id.button_login)
        val registerButton: Button = findViewById(R.id.button_register)
        val forgotPasswordText: TextView = findViewById(R.id.tv_forgot_password)

        loginButton.setOnClickListener {
            val login = loginField.text.toString().trim()
            val pass = passwordField.text.toString().trim()

            if (login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val db = Dbhelper(this, null)
            if (db.checkUser(login, pass)) {
                sharedPref.edit().putBoolean("isLoggedIn", true).putString("login", login).apply()
                Toast.makeText(this, "Добро пожаловать, $login!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ProductListActivity::class.java).putExtra("USER_LOGIN", login))
                finish()
            } else {
                Toast.makeText(this, "Неверные данные", Toast.LENGTH_LONG).show()
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}
