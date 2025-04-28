package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityFruitsBinding

class FruitsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitsBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fruits = listOf(
            Product("Яблоки", "Сочные и сладкие красные яблоки 4 шт", "85", true, R.drawable.apple),
            Product("Бананы", "Спелые бананы из Эквадора веточка", "95", true, R.drawable.banana),
            Product("Киви", "Плоды культурных сортов растений рода Актинидия", "110", true, R.drawable.kivi),
            Product("Клубника", "Клубника богата витаминами и минералами", "300", true, R.drawable.clubnika),
            Product("Инжир", "Восточный", "670", true, R.drawable.ing),
            Product("Голубика", "Сладкая", "340", true, R.drawable.gol)
            )

        adapter = ProductAdapter(fruits) { product ->
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
