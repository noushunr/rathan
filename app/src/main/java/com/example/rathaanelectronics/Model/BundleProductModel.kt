package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class BundleProductModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    inner class Data {
        @SerializedName("offers_id")
        @Expose
        var offers_id: String? = null

        @SerializedName("offers_type")
        @Expose
        var offers_type: String? = null

        @SerializedName("offers_products_id")
        @Expose
        var offers_products_id: String? = null

        @SerializedName("offers_image")
        @Expose
        var offers_image: String? = null

        @SerializedName("offers_text1")
        @Expose
        var offers_text1: String? = null

        @SerializedName("offers_text1_ar")
        @Expose
        var offers_text1_ar: String? = null

        @SerializedName("offers_bundle_price")
        @Expose
        var offers_bundle_price: String? = null

        @SerializedName("offers_bundle_instalprice")
        @Expose
        var offers_bundle_instalprice: String? = null

        @SerializedName("offers_from")
        @Expose
        var offers_from: String? = null

        @SerializedName("offers_to")
        @Expose
        var offers_to: String? = null

        @SerializedName("offers_flat_percent")
        @Expose
        var offers_flat_percent: String? = null

        @SerializedName("offers_amont")
        @Expose
        var offers_amont: String? = null

        @SerializedName("offers_oldprice_show")
        @Expose
        var offers_oldprice_show: String? = null

        @SerializedName("offers_hastimer")
        @Expose
        var offers_hastimer: String? = null

        @SerializedName("offers_status")
        @Expose
        var offers_status: String? = null

        @SerializedName("offers_isdeleted")
        @Expose
        var offers_isdeleted: String? = null

        @SerializedName("offers_date")
        @Expose
        var offers_date: String? = null

        @SerializedName("wishlist_exist")
        @Expose
        var wishlist_exist: String? = null

    }
}