package com.example.catalog

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityBakeryBinding

class BakeryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBakeryBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBakeryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bakeryProducts = listOf(
            Product("Батон", "Свежий пшеничный батон", "35", true, R.drawable.baton),
            Product("Хлеб", "Ржаной хлеб на закваске", "40", false, R.drawable.rye_bread),
            Product("Круассан", "Воздушный круассан с маслом", "55", true, R.drawable.croissant),
            Product("Булочка с корицей", "Сладкая булочка, свежая выпечка", "45", false, R.drawable.cinnamon),
            Product("Хлеб", "Белый тостовый", "30", true, R.drawable.lavash)
        )

        adapter = ProductAdapter(bakeryProducts) { product ->
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
