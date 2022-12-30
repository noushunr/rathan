package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommonResponseModel {
    @SerializedName("status")
    @Expose
    var status: Boolean = false

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("error")
    @Expose
    var error: Error? = null

    data class Error(
        @SerializedName("fname")
        @Expose
        var fname : String?=null,
        @SerializedName("lname")
        @Expose
        var lname : String?=null,
        @SerializedName("adrtitle")
        @Expose
        var adrtitle : String?=null,
        @SerializedName("mail")
        @Expose
        var mail : String?=null,
        @SerializedName("mobile1")
        @Expose
        var mobile1 : String?=null,
        @SerializedName("city")
        @Expose
        var city : String?=null,
        @SerializedName("firstname")
        @Expose
        var firstname : String?=null,
        @SerializedName("lastname")
        @Expose
        var lastname : String?=null,
        @SerializedName("email")
        @Expose
        var email : String?=null,
        @SerializedName("displayname")
        @Expose
        var displayName : String?=null,
    )
}