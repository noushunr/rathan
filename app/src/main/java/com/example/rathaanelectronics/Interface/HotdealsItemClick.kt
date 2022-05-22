package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.SliderModel

interface HotdealsItemClick {

    fun onHotdealsClicked(position: Int, item: DealsModel.Datum?)

    fun onAddToWishlistButtonClick(productId: String)

}