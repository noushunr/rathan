package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class PointsModel {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: PointsData? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    data class PointsData(
        @SerializedName("wallet_amount")
        @Expose
        var walletAmount: String = "",

        @SerializedName("loyalty_amount")
        @Expose
        var loyaltyAmount: String = "",

    )

}