package com.example.rathaanelectronics.Interface

interface RelatedItemClick {
    fun onAddToWishListButtonClicked(productId: String)
    fun onDeleteWishListButtonClicked(productId: String)
    fun onItemClicked(productId: String)
}