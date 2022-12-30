package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class WalletModel {


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
        @SerializedName("details")
        @Expose
        var details: List<Details>? = null,

        @SerializedName("total_debits")
        @Expose
        var totalDebits: String? = null,

        @SerializedName("total_credits")
        @Expose
        var totalCredits: String? = null

        )

    data class Details(
        @SerializedName("wallet_order_id")
        @Expose
        var walletOrderId: String? = null,

        @SerializedName("wallet_amount")
        @Expose
        var walletAmount: String? = null,

        @SerializedName("creditdate")
        @Expose
        var walletDate: String? = null,

        @SerializedName("redeem_order_id")
        @Expose
        var redeemOrderId: String? = null,

        @SerializedName("redeem_amount_KD")
        @Expose
        var redeemAmount: String? = null,

        @SerializedName("redeem_date")
        @Expose
        var redeemDate: String? = null,

        @SerializedName("action")
        @Expose
        var action: String? = null
    )

}