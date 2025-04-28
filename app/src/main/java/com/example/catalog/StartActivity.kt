package com.example.catalog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Найти картинку
        val logoImage = findViewById<ImageView>(R.id.logoImage)

        // Применить анимацию
        val zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_zoom_in)
        logoImage.startAnimation(zoomInAnimation)

        // Задержка и переход на следующий экран
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
            val login = sharedPref.getString("login", "")

            if (isLoggedIn) {
                val intent = Intent(this, ProductListActivity::class.java)
                intent.putExtra("USER_LOGIN", login)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            finish()
        }, 2500) //
    }
}
