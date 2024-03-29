package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class OrderCancelReasons {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    data class Data(
        @SerializedName("return_reason_id")
        @Expose
        var returnReasonId: String? = null,

        @SerializedName("return_reason")
        @Expose
        var returnReason: String? = null,

        @SerializedName("return_reason_ar")
        @Expose
        var returnReasonAr: String? = null


    )

}