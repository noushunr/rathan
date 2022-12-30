package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class GovernarateModel {


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
        @SerializedName("city_governarate")
        @Expose
        var cityGovernarate: String? = null,

        @SerializedName("city_governarate_ar")
        @Expose
        var cityGovernarateAr: String? = null

    )

}