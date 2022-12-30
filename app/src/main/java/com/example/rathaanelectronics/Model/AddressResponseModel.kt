package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AddressResponseModel : Serializable {
    @SerializedName("status")
    @Expose
    var status: Boolean = false

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data{
        @SerializedName("details")
        @Expose
        var details: List<Details>? = null
    }

    inner class Details : Serializable {
        @SerializedName("address_id")
        @Expose
        var addressId: String? = null

        @SerializedName("address_userid")
        @Expose
        var addressUserId: String? = null

        @SerializedName("address_title")
        @Expose
        var addressTitle: String? = null

        @SerializedName("address_fname")
        @Expose
        var addressFname: String? = null

        @SerializedName("address_lname")
        @Expose
        var addressLname: String? = null

        @SerializedName("address_governarate")
        @Expose
        var addressGovernarate: String? = null

        @SerializedName("address_city")
        @Expose
        var addressCity: String? = null

        @SerializedName("address_block")
        @Expose
        var addressBlock: String? = null

        @SerializedName("address_street")
        @Expose
        var addressStreet: String? = null

        @SerializedName("address_avanue")
        @Expose
        var addressAvanue: String? = null

        @SerializedName("address_hb")
        @Expose
        var address_houseBuilding: String? = null

        @SerializedName("address_mail")
        @Expose
        var addressMail: String? = null

        @SerializedName("address_mobile1")
        @Expose
        var addressMobile1: String? = null

        @SerializedName("address_mobile2")
        @Expose
        var addressMobile2: String? = null

        @SerializedName("city_delivery_charge")
        @Expose
        var cityDeliveryzCharge: String? = null

        @SerializedName("city_name")
        @Expose
        var cityName: String? = null

        @SerializedName("city_id")
        @Expose
        var cityId: String? = null


        @SerializedName("city_governarate")
        @Expose
        var cityGovernarate: String? = null

    }
}