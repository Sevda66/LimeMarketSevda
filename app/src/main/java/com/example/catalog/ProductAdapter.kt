package com.example.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ProductAdapter(
    private var productList: List<Product>,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    private var filteredList: List<Product> = productList

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_product_name)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tv_product_description)
        val priceTextView: TextView = itemView.findViewById(R.id.tv_product_price)
        val availableTextView: TextView = itemView.findViewById(R.id.tv_product_availability)
        val productImage: ImageView = itemView.findViewById(R.id.iv_product_image)
        val addToCartButton: Button = itemView.findViewById(R.id.btn_add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredList[position]
        holder.nameTextView.text = product.name
        holder.descriptionTextView.text = product.description
        holder.priceTextView.text = "Цена: ${product.price} ₽"
        holder.availableTextView.text = if (product.available) "В наличии" else "Нет в наличии"
        holder.productImage.setImageResource(product.imageResId)

        holder.addToCartButton.setOnClickListener {
            onAddToCartClick(product)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault())?.trim()
                val result = if (query.isNullOrEmpty()) {
                    productList
                } else {
                    productList.filter {
                        it.name.lowercase(Locale.getDefault()).contains(query) ||
                                it.description.lowercase(Locale.getDefault()).contains(query)
                    }
                }
                return FilterResults().apply { values = result }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Product>
                notifyDataSetChanged()
            }
        }
    }
}
