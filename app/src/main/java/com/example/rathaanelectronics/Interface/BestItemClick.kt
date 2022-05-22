package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.BestDealModel
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.NewArrivalModel
import com.example.rathaanelectronics.Model.SliderModel

interface BestItemClick {

    fun onBestItemClicked(position: Int, item: BestDealModel.Datum?)

    fun onAddToWishListButtonClicked(productId: String)

}