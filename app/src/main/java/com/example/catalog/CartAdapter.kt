package com.example.catalog

import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private var items: MutableList<CartItem>,
    private val onRemoveItem: (CartItem) -> Unit,
    private val onQuantityChanged: () -> Unit // 👈 Новый параметр
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_cart_name)
        val price: TextView = view.findViewById(R.id.tv_cart_price)
        val quantity: TextView = view.findViewById(R.id.tv_cart_quantity)
        val totalPrice: TextView = view.findViewById(R.id.tv_cart_total_price)
        val removeButton: ImageButton = view.findViewById(R.id.btn_remove_cart_item)
        val increaseButton: ImageButton = view.findViewById(R.id.plus)
        val decreaseButton: ImageButton = view.findViewById(R.id.minus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        holder.name.text = item.name
        holder.price.text = "Цена: ${item.price} ₽"
        holder.quantity.text = item.quantity.toString()
        holder.totalPrice.text = "Итого: ${item.getTotalPrice()} ₽"

        holder.removeButton.setOnClickListener {
            onRemoveItem(item)
        }

        holder.increaseButton.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onQuantityChanged() // 👈 Обновляем общую сумму
        }

        holder.decreaseButton.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                onQuantityChanged() // 👈 Обновляем общую сумму
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateCart(newItems: List<CartItem>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }
}
