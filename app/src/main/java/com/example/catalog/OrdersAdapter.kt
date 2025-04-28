package com.example.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrdersAdapter(private var orders: List<Orders>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderInfo: TextView = view.findViewById(R.id.tvOrderInfo)
        val orderDate: TextView = view.findViewById(R.id.tv_order_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderDate.text = order.date
        holder.orderInfo.text = buildString {
            append("Имя: ${order.name}\n")
            append("Адрес: ${order.address}\n")
            append("Телефон: ${order.phone}\n")
            append("Оплата: ${order.paymentMethod}\n")
            append("Товары:\n")
            order.items.forEach {
                append("- ${it.name} x${it.quantity}\n")
            }
            append("Итого: ${order.totalPrice} ₽")
        }
    }
}
