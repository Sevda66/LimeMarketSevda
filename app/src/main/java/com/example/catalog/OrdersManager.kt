package com.example.catalog

object OrdersManager {
    private val orders = mutableListOf<Orders>()

    fun addOrder(order: Orders) {
        orders.add(order)
    }

    fun getOrders(): List<Orders> = orders

    fun clearOrders() {
        orders.clear()
    }
}
