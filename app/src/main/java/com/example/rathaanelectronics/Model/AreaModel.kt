package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class AreaModel {


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
        @SerializedName("city_name")
        @Expose
        var cityName: String?=null,

        @SerializedName("city_name_ar")
        @Expose
        var cityNameAr: String?=null,

        @SerializedName("city_id")
        @Expose
        var cityId: String?=null


    )

}