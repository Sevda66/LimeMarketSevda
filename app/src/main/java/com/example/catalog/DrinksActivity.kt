package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityDrinksBinding

class DrinksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrinksBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drinks = listOf(
            Product("Вода", "Минеральная вода без газа", "40", true, R.drawable.bij),
            Product("Сок апельсиновый Pulpy", "100% натуральный сок", "85", true, R.drawable.orange_juice),
            Product("Чай", "Азерчай черный листовой", "65", false, R.drawable.black_tea),
            Product("Вода", "Газированная", "45", false, R.drawable.aaaa),
            Product("Сок", "Яблочный литр", "220", false, R.drawable.soki),
            Product("Чай", "Зеленый чай", "560", false, R.drawable.drdr),
            Product("Кофе", "Ароматный", "679", false, R.drawable.aaa),
        )

        adapter = ProductAdapter(drinks) { product ->
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
