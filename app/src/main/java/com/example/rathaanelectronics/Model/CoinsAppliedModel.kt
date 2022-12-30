package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class CoinsAppliedModel {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    data class Data(

        @SerializedName("loyalty_applied_total")
        @Expose
        var loyaltyAppliedTotal: String? = null,

        @SerializedName("wallet_applied_total")
        @Expose
        var walletAppliedTotal: String? = null

        )


}