package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GuestTokenModel {
    @SerializedName("status")
    @Expose
    var status: Boolean = false

    @SerializedName("data")
    @Expose
    var data:Data? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    inner class Data{
        @SerializedName("guesttoken_id")
        @Expose
        var guesttokenId: String? = null


        @SerializedName("guesttoken_guest_id")
        @Expose
        var guesttokenGuestId: String? = null


        @SerializedName("guesttoken_device_token")
        @Expose
        var guesttokenDeviceToken: String? = null


        @SerializedName("guesttoken_access_token")
        @Expose
        var guesttokenAccessToken: String? = null

    }

}