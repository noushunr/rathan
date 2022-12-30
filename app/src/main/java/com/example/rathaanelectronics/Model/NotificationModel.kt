package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class NotificationModel {

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
        @SerializedName("title")
        @Expose
        var title: String?=null,

        @SerializedName("message")
        @Expose
        var message: String?=null,

        @SerializedName("created_at")
        @Expose
        var createdAt: String?=null


    )

}