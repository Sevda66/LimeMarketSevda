package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityCannedBinding


class CannedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCannedBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCannedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cannedProducts = listOf(
            Product("Тушёнка", "Говядина тушёная в собственном соку", "170", true, R.drawable.tushonka),
            Product("Горошек", "Зелёный консервированный горошек", "60", true, R.drawable.green_peas),
            Product("Кукуруза", "Сладкая консервированная кукуруза", "65", true, R.drawable.corn_canned),
            Product("Сардины", "Рыбные консервы в масле", "95", true, R.drawable.sardines),
            Product("Аджика", "Острая аджика домашнего приготовления", "85", false, R.drawable.adzhika)
        )

        adapter = ProductAdapter(cannedProducts) { product ->
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
