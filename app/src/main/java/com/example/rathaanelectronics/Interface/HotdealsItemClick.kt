package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.Product
import com.example.rathaanelectronics.Model.SliderModel

interface HotdealsItemClick {

    fun onHotdealsClicked(position: Int, item: Product?)

    fun onAddToWishlistButtonClick(productId: String)

    fun onDeleteFromWishListButtonClick(productId:String)

}