package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class CartCountModel {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: CartData? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    data class CartData(
        @SerializedName("total")
        @Expose
        var total: Double = 0.0,

        @SerializedName("count")
        @Expose
        var count: Int = 0,

        @SerializedName("wishlist_count")
        @Expose
        var wishlistCount: Int = 0

    )

}