package com.example.rathaanelectronics.Interface

interface ManageCartItem {
    fun onDeleteCartItem(cartId: String?)
    fun onUpdateCartQuantity(cartId: String?, quantity: String)
}