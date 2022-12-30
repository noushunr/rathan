package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductDetailsModel {
    @SerializedName("status")
    @Expose
    var status: Boolean = false

    @SerializedName("data")
    @Expose
    var data: ProductData? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("related_products")
    @Expose
    var relatedProducts: List<Product>? = null

    @SerializedName("prodattrs")
    @Expose
    var prodAttrs: List<ProdAttrs>? = null



    inner class ProductData{
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

        @SerializedName("product_desc")
        @Expose
        var productDesc: String? = null

        @SerializedName("product_desc_arab")
        @Expose
        var productDescArab: String? = null

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

        @SerializedName("product_available_ar")
        @Expose
        var productAvailableArabic: String? = null

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

        @SerializedName("product_delivery_or_pickup_title_ar")
        @Expose
        var productDeliveryOrPickupTitleArab: String? = null

        @SerializedName("product_rate")
        @Expose
        var productRate: String? = null

        @SerializedName("product_purchase_rate")
        @Expose
        var productPurchaseRate: String? = null

        @SerializedName("product_sell_price")
        @Expose
        var productSellPrice: String? = null

        @SerializedName("product_spoffer_price")
        @Expose
        var productSpofferPrice: String? = null

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

        @SerializedName("product_available_for")
        @Expose
        var productAvailableFor: String? = null

        @SerializedName("loyalty_points")
        @Expose
        var loyaltyPoints: Int? = 0

        @SerializedName("product_spoffer_oldprice_show")
        @Expose
        var productOldPriceShow: String? = "0"

    }

    inner class RelatedProducts{
        @SerializedName("product_id")
        @Expose
        var productId: String? = null

        @SerializedName("product_name")
        @Expose
        var productName: String? = null

        @SerializedName("product_name_arab")
        @Expose
        var productNameArab: String? = null

        @SerializedName("product_sell_price")
        @Expose
        var productSellPrice: String? = null

        @SerializedName("product_spoffer_price")
        @Expose
        var productSpofferPrice: String? = null

        @SerializedName("hot_price")
        @Expose
        var hotPrice: String? = null

        @SerializedName("prod_frond_img")
        @Expose
        var prodFrondImg: String? = null

        @SerializedName("brand_name")
        @Expose
        var brandName: String? = null

        @SerializedName("wishlist_exist")
        @Expose
        var wishlistExist: Int? = 0



    }

    inner class ProdAttrs{
        @SerializedName("attribute_id")
        @Expose
        var attributeId: String? = null

        @SerializedName("categories")
        @Expose
        var categories: String? = null

        @SerializedName("attribute_name")
        @Expose
        var attributeName: String? = null

        @SerializedName("attribute_value")
        @Expose
        var attributeValue: String? = null
    }

}