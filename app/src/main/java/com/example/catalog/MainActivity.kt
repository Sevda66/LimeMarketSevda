package com.example.catalog

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.itprogerapp.Dbhelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
        if (sharedPref.getBoolean("isLoggedIn", false)) {
            val login = sharedPref.getString("login", "")!!
            startActivity(Intent(this, ProductListActivity::class.java).putExtra("USER_LOGIN", login))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_pass)
        val registerButton: Button = findViewById(R.id.button_reg)
        val loginButton: Button = findViewById(R.id.button_go_to_login)

        registerButton.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            // Проверка на пустые поля
            if (login.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Проверка на корректность электронной почты
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Введите корректный адрес электронной почты", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Проверка длины пароля
            if (pass.length < 6 || pass.length > 9) {
                Toast.makeText(this, "Пароль должен быть от 6 до 9 символов", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Добавление пользователя в базу данных
            val db = Dbhelper(this, null)
            db.addUser(User(login, email, pass))

            sharedPref.edit().putBoolean("isLoggedIn", true).putString("login", login).apply()
            Toast.makeText(this, "Пользователь $login зарегистрирован", Toast.LENGTH_LONG).show()

            startActivity(Intent(this, ProductListActivity::class.java).putExtra("USER_LOGIN", login))
            finish()
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
