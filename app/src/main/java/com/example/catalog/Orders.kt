package com.example.catalog

data class Orders(
    val name: String,
    val address: String,
    val phone: String,
    val paymentMethod: String,
    val items: List<CartItem>,
    val totalPrice: Int,
    val date: String
)
