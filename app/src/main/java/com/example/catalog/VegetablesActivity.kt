package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityVegetablesBinding

class VegetablesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVegetablesBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegetablesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vegetables = listOf(
            Product("Салат", "Айсберг", "35", true, R.drawable.list),
            Product("Лук", "Обычный.", "25", true, R.drawable.carrot),
            Product("Огурцы", "Свежие, хрустящие огурцы упаковка", "50", true, R.drawable.cucumber),
            Product("Помидоры", "Помидоры черри с грядки 1 кг", "65", true, R.drawable.tomato),
            Product("Картошка", "Имеет сочную и рассыпчатую мякоть 1 кг", "25", true, R.drawable.potato),
            Product("Помидоры", "Розовые 1 кг", "230", true, R.drawable.fols),
        )


        adapter = ProductAdapter(vegetables) { product ->
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
