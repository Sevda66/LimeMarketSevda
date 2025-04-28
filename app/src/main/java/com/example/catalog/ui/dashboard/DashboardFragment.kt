package com.example.catalog

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardFragment : Fragment() {

    private lateinit var totalPriceTextView: TextView
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalPriceTextView = view.findViewById(R.id.tv_total_price)

        val cartItems = CartManager.getItems().toMutableList()

        // 🔧 Обновленный адаптер с onQuantityChanged
        cartAdapter = CartAdapter(
            cartItems,
            onRemoveItem = { itemToRemove ->
                CartManager.removeItem(itemToRemove)
                updateCartView()
            },
            onQuantityChanged = {
                updateTotalPrice() // 👈 Обновляем сумму при изменении количества
            }
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_cart)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = cartAdapter

        updateTotalPrice()

        view.findViewById<Button>(R.id.btn_clear_cart).setOnClickListener {
            CartManager.clearCart()
            CartManager.clearPriceOffset()
            updateCartView()
        }

        view.findViewById<Button>(R.id.btn_checkout).setOnClickListener {
            val intent = Intent(requireContext(), DeliveryActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.btn_apply_bonus).setOnClickListener {
            applyBonus()
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = CartManager.getTotalPrice()
        totalPriceTextView.text = "Общая сумма: $totalPrice ₽"
    }

    private fun updateCartView() {
        cartAdapter.updateCart(CartManager.getItems())
        updateTotalPrice()
    }

    private fun applyBonus() {
        val totalPrice = CartManager.getTotalPrice()
        val bonus = BonusManager.getBonus()

        if (totalPrice == 0) {
            Toast.makeText(requireContext(), "Корзина пуста", Toast.LENGTH_SHORT).show()
            return
        }

        if (bonus > 0) {
            val appliedBonus = minOf(bonus, totalPrice)
            BonusManager.applyBonus(appliedBonus)
            CartManager.reduceTotalPrice(appliedBonus)
            updateTotalPrice()
            Toast.makeText(requireContext(), "Бонусы применены: -$appliedBonus ₽", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "У вас нет бонусов для применения.", Toast.LENGTH_SHORT).show()
        }
    }
}
