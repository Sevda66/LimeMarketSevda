package com.example.catalog

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.itprogerapp.Dbhelper

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val newPasswordField = findViewById<EditText>(R.id.new_password_field)
        val confirmPasswordField = findViewById<EditText>(R.id.confirm_password_field)
        val saveButton = findViewById<Button>(R.id.button_save_password)

        val email = intent.getStringExtra("USER_EMAIL") ?: return
        val db = Dbhelper(this, null)

        saveButton.setOnClickListener {
            val newPassword = newPasswordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = db.updatePasswordByEmail(email, newPassword)
            if (success) {
                Toast.makeText(this, "Пароль успешно обновлен", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Ошибка при обновлении пароля", Toast.LENGTH_LONG).show()
            }
        }
    }
}
