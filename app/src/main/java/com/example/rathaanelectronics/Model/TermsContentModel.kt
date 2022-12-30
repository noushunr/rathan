package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class TermsContentModel {


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

        @SerializedName("termsale_id")
        @Expose
        var termsaleId: String = "",

        @SerializedName("termsale_content")
        @Expose
        var termsaleContent: String = "",

        @SerializedName("termsale_content_arab")
        @Expose
        var termsaleContentAr: String = "",


        )

}