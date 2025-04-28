package com.example.catalog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.catalog.ProductListActivity
import com.example.catalog.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userLogin = intent.getStringExtra("USER_LOGIN")
        val welcomeText: TextView = findViewById(R.id.welcome_text)
        welcomeText.text = "Добро пожаловать, $userLogin!"


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2000 мс = 2 секунды
    }
}