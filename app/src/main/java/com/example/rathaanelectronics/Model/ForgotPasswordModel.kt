package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.ForgotPasswordModel.Responce

class ForgotPasswordModel {
    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("responce")
        @Expose
        var responce: Responce? = null

        @SerializedName("message")
        @Expose
        var message: String? = null
    }

    inner class Responce {
        @SerializedName("forgotpassword")
        @Expose
        var forgotpassword: String? = null
    }
}