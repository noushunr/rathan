package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class CouponApplyModel {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: CouponData? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    data class CouponData(
        @SerializedName("status")
        @Expose
        var status: Int = 0,

        @SerializedName("comments")
        @Expose
        var comments: String?=null,

        @SerializedName("discountamnt")
        @Expose
        var discountAmount: String?=null,

        @SerializedName("discnote")
        @Expose
        var discNote: String?=null,

        @SerializedName("coupontype")
        @Expose
        var couponType: String?=null,

    )

}