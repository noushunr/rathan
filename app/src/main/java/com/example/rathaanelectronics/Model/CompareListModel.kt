package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class CompareListModel {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: MutableList<Data>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    inner class Data {
        @SerializedName("compare_id")
        @Expose
        var compareId: String? = null

        @SerializedName("compare_prod_id")
        @Expose
        var compareProdId: String? = null

        @SerializedName("compare_user_id")
        @Expose
        var compareUserId: String? = null

        @SerializedName("compare_date")
        @Expose
        var compareDate: String? = null
        @SerializedName("product_id")
        @Expose
        var productId: String? = null

        @SerializedName("product_name")
        @Expose
        var productName: String? = null

        @SerializedName("product_name_arab")
        @Expose
        var productNameArab: String? = null

        @SerializedName("product_short_desc")
        @Expose
        var productShortDesc: String? = null

        @SerializedName("product_short_desc_arab")
        @Expose
        var productShortDescArab: String? = null

        @SerializedName("product_instruction")
        @Expose
        var productInstruction: String? = null

        @SerializedName("product_instruction_arab")
        @Expose
        var productInstructionArab: String? = null

        @SerializedName("product_image")
        @Expose
        var productImage: String? = null

        @SerializedName("prod_frond_img")
        @Expose
        var prodFrondImg: String? = null

        @SerializedName("product_available")
        @Expose
        var productAvailable: String? = null

        @SerializedName("product_sku")
        @Expose
        var productSKU: String? = null

        @SerializedName("brand_name")
        @Expose
        var brandName: String? = null

        @SerializedName("brand_pic")
        @Expose
        var brandPic: String? = null

        @SerializedName("brand_name_arab")
        @Expose
        var brandNameArab: String? = null

        @SerializedName("product_delivery_or_pickup_title")
        @Expose
        var productDeliveryOrPickupTitle: String? = null

        @SerializedName("product_delivery_or_pickup")
        @Expose
        var productDeliveryOrPickup: String? = null

        @SerializedName("product_rate")
        @Expose
        var productRate: String? = null

        @SerializedName("product_purchase_rate")
        @Expose
        var productPurchaseRate: String? = null

        @SerializedName("product_sell_price")
        @Expose
        var productSellPrice: String? = null

        @SerializedName("product_pickupstore")
        @Expose
        var productPickupStore: String? = null

        @SerializedName("product_sameday_delivery")
        @Expose
        var productSamedayDelivery: String? = null

        @SerializedName("hot_price")
        @Expose
        var hotPrice: String? = null

        @SerializedName("product_installation")
        @Expose
        var productInstallation: String? = null

        @SerializedName("wishlist_exist")
        @Expose
        var wishlist_exist: String? = null
    }
}