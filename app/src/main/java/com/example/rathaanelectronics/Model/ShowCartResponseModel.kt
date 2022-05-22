package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ShowCartResponseModel {
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
        @SerializedName("cart_items")
        @Expose
        var cartItems: List<CartItems>? = null

        @SerializedName("cart_total")
        @Expose
        var cartTotal: String? = null

        @SerializedName("installation_total")
        @Expose
        var installationTotal: String? = null

        @SerializedName("cart_total_with_installation")
        @Expose
        var cartTotalWithInstallation: String? = null

        class CartItems{
            @SerializedName("cart_id")
            @Expose
            var cartId: String? = null

            @SerializedName("cart_quantity")
            @Expose
            var cartQuantity: String? = null

            @SerializedName("cart_price")
            @Expose
            var cartPrice: String? = null

            @SerializedName("cart_amount")
            @Expose
            var cartAmount: String? = null

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
        }

    }

}