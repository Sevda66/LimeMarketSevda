package com.example.catalog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.databinding.ActivityGrainsBinding

class GrainsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGrainsBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrainsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val products = listOf(
            Product("Гречка зелёная", "Полезная крупа без обжарки, подходит для диетического питания", "129", true, R.drawable.greens_buckwheat),
            Product("Манная крупа", "Нежная текстура, подходит для каш, десертов и запеканок", "59", true, R.drawable.semolina),
            Product("Овсяная крупа", "Богата клетчаткой, полезна для сердца и пищеварения", "79", false, R.drawable.oats),
            Product("Рисовая крупа", "Круглозерный рис, идеален для гарниров, плова и каш", "99", true, R.drawable.rice),
            Product("Кукурузная крупа", "Жёлтая крупа, отлично подходит для каши, мамалыги и запеканок", "75", false, R.drawable.corn),
            Product("Булгур", "Среднеразмерная пшеничная крупа, быстро готовится, хороша в салатах и гарнирах", "105", true, R.drawable.bulgur)
        )

        adapter = ProductAdapter(products) { product ->
            if (product.available) {
                CartManager.addItem(CartItem(product.name, product.price.toInt()))
                Toast.makeText(this, "${product.name} добавлен в корзину", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "${product.name} нет в наличии", Toast.LENGTH_SHORT).show()
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
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
