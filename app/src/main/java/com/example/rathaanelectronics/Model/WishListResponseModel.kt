package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.sql.StatementEvent

class WishListResponseModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    class Data {
        @SerializedName("details")
        @Expose
        var details: List<Details>? = null

        class Details{
            @SerializedName("wishlist_id")
            @Expose
            var wishlistId: String? = null

            @SerializedName("wishlist_prod_id")
            @Expose
            var wishlistProdId: String? = null

            @SerializedName("wishlist_bundle_offerid")
            @Expose
            var wishlistBundleOfferid: String? = null

            @SerializedName("product_id")
            @Expose
            var productId: String? = null

            @SerializedName("product_purchase_rate")
            @Expose
            var productPurchaseRate: String? = null

            @SerializedName("product_rate")
            @Expose
            var productRate: String? = null

            @SerializedName("product_sell_price")
            @Expose
            var productSellPrice: String? = null

            @SerializedName("product_image")
            @Expose
            var productImage: String? = null

            @SerializedName("product_name")
            @Expose
            var productName: String? = null

            @SerializedName("product_name_arab")
            @Expose
            var productNameArab: String? = null

            @SerializedName("product_quantity")
            @Expose
            var productQuantity: String? = null

            @SerializedName("offers_bundle_price")
            @Expose
            var offersBundlePrice: String? = null

            @SerializedName("offers_bundle_title")
            @Expose
            var offersBundleTitle: String? = null

            @SerializedName("offers_image")
            @Expose
            var offersImage: String? = null

            @SerializedName("offers_bundle_title_arab")
            @Expose
            var offersBundleTitleArab: String? = null
        }

    }

}