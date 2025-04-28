package com.example.catalog

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BonusCardActivity : AppCompatActivity() {

    private lateinit var tvBonusBalance: TextView
    private lateinit var btnLogout: Button
    private lateinit var ivBonusImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bonus_card)

        tvBonusBalance = findViewById(R.id.tv_bonus_balance)
        btnLogout = findViewById(R.id.btn_logout)
        ivBonusImage = findViewById(R.id.iv_bonus_image)

        updateBonusText()

        ivBonusImage.setOnClickListener {
            Toast.makeText(this, "Вы нажали на бонусную карту!", Toast.LENGTH_SHORT).show()
        }



        btnLogout.setOnClickListener {
            finish()
        }
    }

    private fun updateBonusText() {
        val points = BonusManager.getBonus()
        tvBonusBalance.text = "Ваши бонусы: $points"
    }
}
