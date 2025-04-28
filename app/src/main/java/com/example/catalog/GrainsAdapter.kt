package com.example.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class GrainsAdapter(
    private val context: Context,
    private val originalList: List<Product>
) : RecyclerView.Adapter<GrainsAdapter.ProductViewHolder>(), Filterable {

    private var filteredList: List<Product> = originalList

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.iv_product_image)
        val name: TextView = view.findViewById(R.id.tv_product_name)
        val price: TextView = view.findViewById(R.id.tv_product_price)
        val availability: TextView = view.findViewById(R.id.tv_product_availability)
        val addButton: Button = view.findViewById(R.id.btn_add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredList[position]

        holder.name.text = product.name
        holder.price.text = "Цена: ${product.price} ₽"
        holder.availability.text = if (product.available) "В наличии" else "Нет в наличии"
        holder.image.setImageResource(product.imageResId)

        holder.addButton.setOnClickListener {
            CartManager.addItem(CartItem(product.name, product.price.toInt()))
            Toast.makeText(context, "${product.name} добавлен в корзину", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault())?.trim()
                val result = if (query.isNullOrEmpty()) {
                    originalList
                } else {
                    originalList.filter {
                        it.name.lowercase(Locale.getDefault()).contains(query)
                                it.description.lowercase(Locale.getDefault()).contains(query)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = result
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Product>
                notifyDataSetChanged()
            }
        }
    }
}
