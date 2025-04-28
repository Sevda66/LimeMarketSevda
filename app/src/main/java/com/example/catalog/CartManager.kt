package com.example.catalog

object CartManager {
    private val items = mutableListOf<CartItem>()
    private var totalPriceOffset: Int = 0

    fun getItems(): List<CartItem> = items

    fun addItem(item: CartItem) {
        val existing = items.find { it.name == item.name }
        if (existing != null) {
            existing.quantity++
        } else {
            items.add(item)
        }
    }

    fun removeItem(item: CartItem) {
        items.remove(item)
    }

    fun clearCart() {
        items.clear()
    }

    fun reduceTotalPrice(amount: Int) {
        totalPriceOffset += amount
    }

    fun clearPriceOffset() {
        totalPriceOffset = 0
    }

    fun getTotalPrice(): Int {
        val total = items.sumOf { it.getTotalPrice() }
        return (total - totalPriceOffset).coerceAtLeast(0)
    }
}
