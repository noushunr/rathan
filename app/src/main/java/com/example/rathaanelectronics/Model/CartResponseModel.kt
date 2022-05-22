package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CartResponseModel {
    @SerializedName("status")
    @Expose
    var status: Boolean = false

    @SerializedName("message")
    @Expose
    var message: String? = null

}