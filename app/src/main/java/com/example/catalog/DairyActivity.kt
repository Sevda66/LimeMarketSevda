package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityDairyBinding

class DairyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDairyBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDairyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dairyProducts = listOf(
            Product("Молоко", "Свежайшее коровье молоко 3.2%", "65", true, R.drawable.milka),
            Product("Сыр", "Российский полутвёрдый сыр", "350", true, R.drawable.cheese),
            Product("Йогурт", "Натуральный йогурт без сахара", "49", true, R.drawable.yogurt),
            Product("Сметана", "Домашняя сметана 20%", "79", false, R.drawable.ds),
            Product("Кефир", "Полезный напиток для пищеварения", "55", true, R.drawable.kefir)
        )

        adapter = ProductAdapter(dairyProducts) { product ->
            if (product.available) {
                CartManager.addItem(CartItem(product.name, product.price.toInt()))
                Toast.makeText(this, "${product.name} добавлен в корзину", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "${product.name} нет в наличии", Toast.LENGTH_SHORT).show()
            }
        }


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
