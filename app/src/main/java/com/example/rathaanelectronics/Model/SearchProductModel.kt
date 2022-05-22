package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class SearchProductModel {

    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<DealsModel.Datum>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null


}