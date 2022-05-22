package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class TimerOfferModel {

    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: MutableList<Data>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

}