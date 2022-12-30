package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class DeliveryDaysCheckModel {


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
        @SerializedName("deliverydays")
        @Expose
        var deliveryDays: String = "",

        @SerializedName("deliverytime")
        @Expose
        var deliveryTime: String = "",

        @SerializedName("deliverysamedaycharge")
        @Expose
        var deliverySameDayCharge: String = "",

//        @SerializedName("choose_date")
//        @Expose
//        var chooseDate: Int = 0,


        )

}