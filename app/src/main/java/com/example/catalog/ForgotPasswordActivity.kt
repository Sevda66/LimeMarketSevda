package com.example.catalog

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.itprogerapp.Dbhelper

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailField = findViewById<EditText>(R.id.email_field)
        val newPasswordField = findViewById<EditText>(R.id.new_password_field)
        val resetButton = findViewById<Button>(R.id.button_reset_password)

        resetButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val newPass = newPasswordField.text.toString().trim()

            if (email.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Некорректный email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = Dbhelper(this, null)
            if (!db.isEmailExists(email)) {
                Toast.makeText(this, "Email не найден", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = db.updatePasswordByEmail(email, newPass)
            if (success) {
                Toast.makeText(this, "Пароль обновлён!", Toast.LENGTH_LONG).show()
                finish() // Закрыть и вернуться на экран входа
            } else {
                Toast.makeText(this, "Ошибка при обновлении пароля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
