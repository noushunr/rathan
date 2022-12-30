package com.example.rathaanelectronics.Interface

interface ManageWishlistItemClick {
    fun addToCart(productId: String?, quantity: String?)
    fun addToBundleCart(productId: String?, quantity: String?)
    fun deleteItem(productId: String?)
}