package com.example.catalog

data class CartItem(

    val name: String,
    val price: Int,
    var quantity: Int = 1
) {
    fun getTotalPrice(): Int {
        return price * quantity
    }
}
