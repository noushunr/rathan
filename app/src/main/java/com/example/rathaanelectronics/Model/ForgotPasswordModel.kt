package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ForgotPasswordModel {
    @SerializedName("data")
    @Expose
    var data: Data? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
    inner class Data {
        @SerializedName("forgotpassword")
        @Expose
        var forgotpassword: String? = null
    }

}