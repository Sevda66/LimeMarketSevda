package com.example.catalog

data class Product(
    val name: String,
    val description: String,
    val price: String,
    val available: Boolean,
    val imageResId: Int
)
