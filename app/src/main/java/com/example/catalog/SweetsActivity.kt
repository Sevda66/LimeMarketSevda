package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivitySweetsBinding

class SweetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySweetsBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySweetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sweets = listOf(
            Product("Шоколад", "Белый воздушный", "120", true, R.drawable.chocolate),
            Product("Печенье", "Шоколадное печенье", "90", true, R.drawable.cookies),
            Product("Конфеты", "Raffaello", "150", false, R.drawable.candies),
            Product("Торт", "Медовик", "85", true, R.drawable.mars),
            Product("Печенье", "Milka с шоколадной крошкой", "230", true, R.drawable.cook),
        )

        adapter = ProductAdapter(sweets) { product ->
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
