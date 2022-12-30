package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.Data
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.SliderModel

interface TimerItemClick {

    fun onHotdealsClicked(position: Int, item: Data?)

    fun onAddToWishlistButtonClick(productId: String)

    fun onDeleteFromWishListButtonClick(productId: String)

}