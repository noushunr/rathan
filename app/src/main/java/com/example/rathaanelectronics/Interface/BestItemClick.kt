package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.*

interface BestItemClick {

    fun onBestItemClicked(position: Int, item: Product?)

    fun onAddToWishListButtonClicked(productId: String)

}