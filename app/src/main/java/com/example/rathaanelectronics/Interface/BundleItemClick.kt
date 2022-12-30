package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.BundleProductModel
import com.example.rathaanelectronics.Model.Data
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.SliderModel

interface BundleItemClick {

    fun onItemClicked(position: Int, item: BundleProductModel.Data?)

    fun onAddToWishlistButtonClick(productId: String)

    fun onDeleteFromWishListButtonClick(productId: String)

}