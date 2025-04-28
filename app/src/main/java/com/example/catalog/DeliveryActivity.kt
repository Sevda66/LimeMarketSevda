package com.example.catalog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.catalog.databinding.ActivityDeliveryBinding
import java.text.SimpleDateFormat
import java.util.*

class DeliveryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeliveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmDelivery.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            var phone = binding.etPhone.text.toString().trim()
                .replace("\\s".toRegex(), "")
                .replace("[()-]".toRegex(), "") // убираем скобки и дефисы


            if (phone.startsWith("8")) {
                phone = "+7" + phone.substring(1)
            } else if (phone.length == 10 && phone.all { it.isDigit() }) {
                phone = "+7$phone"
            }

            binding.etPhone.setText(phone)


            if (!phone.matches(Regex("""\+7\d{10}"""))) {
                Toast.makeText(this, "Номер должен быть в формате +7XXXXXXXXXX (11 цифр)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedPaymentId = binding.rgPayment.checkedRadioButtonId
            val paymentMethod = when (selectedPaymentId) {
                R.id.rb_cash -> "Наличные"
                R.id.rb_card -> "Карта"
                else -> null
            }

            if (name.isEmpty() || address.isEmpty() || paymentMethod == null) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val items = CartManager.getItems().map { it.copy() }
            val totalPrice = CartManager.getTotalPrice()
            val bonusToAdd = (totalPrice * 0.1).toInt()
            BonusManager.addBonus(bonusToAdd)

            val date = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
            val order = Orders(name, address, phone, paymentMethod, items, totalPrice, date)
            OrdersManager.addOrder(order)

            AlertDialog.Builder(this)
                .setTitle("Заказ оформлен")
                .setMessage("Начислено $bonusToAdd бонусов.\nСпасибо, $name!")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .setCancelable(false)
                .show()

            CartManager.clearCart()
        }
    }
}
