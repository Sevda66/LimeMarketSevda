package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityMeatBinding

class MeatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeatBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val meatProducts = listOf(
            Product("Свинина", "Свежая свиная вырезка кг", "399", true, R.drawable.pork),
            Product("Фарш", "Куринный", "125", true, R.drawable.farsh),
            Product("Говядина", "Мраморная говядина высшего сорта кг", "489", true, R.drawable.beef),
            Product("Курица", "Охлаждённое куриное филе", "299", true, R.drawable.chicken),
            Product("Фарш", "Говяжий", "249", false, R.drawable.minced_meat),
            Product("Колбаса", "Докторская", "199", true, R.drawable.sausage),
            Product("Фарш", "Свиной", "79", true, R.drawable.far),
            Product("Колбаса", "Копченная", "185", true, R.drawable.fsh),
        )

        adapter = ProductAdapter(meatProducts) { product ->
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
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        binding.btnBack.setOnClickListener { finish() }
    }
}
