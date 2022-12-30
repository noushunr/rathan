package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class DeliveryDateCheckModel {


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
        @SerializedName("options")
        @Expose
        var options: Options? = null,
        @SerializedName("message")
        @Expose
        var message: String? = null

        )

    data class Options(
        @SerializedName("date_status")
        @Expose
        var dateStatus: Int = 0,

        @SerializedName("same_day")
        @Expose
        var sameDay: Int = 0,

        @SerializedName("next_day")
        @Expose
        var nextDay: Int = 0,

        @SerializedName("choose_date")
        @Expose
        var chooseDate: Int = 0,


        )

}