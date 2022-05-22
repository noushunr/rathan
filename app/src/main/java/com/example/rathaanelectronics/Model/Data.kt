package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Noushad N on 22-03-2022.
 */
class Data : Serializable {

    @SerializedName("product_id")
    @Expose
    var productId: String? = null

    @SerializedName("product_name")
    @Expose
    var productName: String? = null

    @SerializedName("product_name_arab")
    @Expose
    var productNameArab: String? = null

    @SerializedName("product_spoffer_stat")
    @Expose
    var productSpofferStat: String? = null

    @SerializedName("product_offer_stat")
    @Expose
    var productOfferStat: String? = null

    @SerializedName("product_spoffer_start")
    @Expose
    var productSpofferStart: String? = null

    @SerializedName("product_spoffer_end")
    @Expose
    var productSpofferEnd: String? = null

    @SerializedName("product_spoffer_price")
    @Expose
    var productSpofferPrice: String? = null

    @SerializedName("product_sell_price")
    @Expose
    var productSellPrice: String? = null

    @SerializedName("prod_frond_img")
    @Expose
    var prodFrondImg: String? = null

    @SerializedName("product_image")
    @Expose
    var productImage: String? = null

    @SerializedName("brand_id")
    @Expose
    var brandId: String? = null

    @SerializedName("brand_name")
    @Expose
    var brandName: String? = null

    @SerializedName("hot_price")
    @Expose
    var hotPrice: String? = null

    @SerializedName("product_single_quantity")
    @Expose
    var productSingleQuantity: String? = null
}