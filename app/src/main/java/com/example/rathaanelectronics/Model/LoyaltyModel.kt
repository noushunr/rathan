package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class LoyaltyModel {


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

        @SerializedName("total_redeemed")
        @Expose
        var totalRedeemed: String? = null,

        @SerializedName("total_loyalty_point_earned")
        @Expose
        var totalEarned: String? = null,

        @SerializedName("loyalty_amount_balance")
        @Expose
        var amountBalance: String? = null

        )

    data class Details(
        @SerializedName("dc_prod_name")
        @Expose
        var productName: String? = null,

        @SerializedName("product_name_arab")
        @Expose
        var productNameAr: String? = null,

        @SerializedName("dc_prod_loyalty")
        @Expose
        var productLoyalty: String? = null,

        @SerializedName("dc_date")
        @Expose
        var date: String? = null,

        @SerializedName("creditdate")
        @Expose
        var creditDate: String? = null,

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