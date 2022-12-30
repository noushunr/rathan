package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.NewArrivalModel
import com.example.rathaanelectronics.Model.SliderModel

interface NewArrivalItemClick {

    fun onNewArrivalClicked(position: Int, item: NewArrivalModel.Datum?)

    fun onAddToWishListButtonClick(productId:String)

    fun onDeleteFromWishListButtonClick(productId:String)
}