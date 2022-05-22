package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class CategoryProductModel {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: CategoryProductData? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    data class CategoryProductData (
        @SerializedName("subcategories")
        @Expose
        var subcategories: MutableList<SubCategory>? = null,

        @SerializedName("products")
        @Expose
        var products: MutableList<CategoryProduct>? = null
    )

    data class CategoryProduct (
        @SerializedName("product_id")
        @Expose
        var productId: String? = null,

        @SerializedName("prod_topselling")
        @Expose
        var prodTopSelling: String? = null,

        @SerializedName("prod_toprated")
        @Expose
        var prodTopRated: String? = null,

        @SerializedName("product_name")
        @Expose
        var productName: String? = null,

        @SerializedName("product_name_arab")
        @Expose
        var productNameArab: String? = null,

        @SerializedName("product_single_quantity")
        @Expose
        var productSingleQuantity: String? = null,

        @SerializedName("product_sell_price")
        @Expose
        var productSellPrice: String? = null,

        @SerializedName("product_image")
        @Expose
        var prodImg: String? = null,

        @SerializedName("product_available")
        @Expose
        var productAvailable: String? = null,

        @SerializedName("product_offer_stat")
        @Expose
        var productOfferStat: String? = null,

        @SerializedName("product_spoffer_stat")
        @Expose
        var productSpofferStat: String? = null,

        @SerializedName("product_spoffer_start")
        @Expose
        var productSpofferStart: String? = null,

        @SerializedName("product_spoffer_end")
        @Expose
        var productSpofferEnd: String? = null,

        @SerializedName("product_spoffer_price")
        @Expose
        var productSpofferPrice: String? = null,

        @SerializedName("prod_frond_img")
        @Expose
        var prodFrondImg: String? = null,

        @SerializedName("category_id")
        @Expose
        var categoryId: String? = null,

        @SerializedName("category_label")
        @Expose
        var categoryLabel: String? = null,

        @SerializedName("subcategory_id")
        @Expose
        var subcategoryId: String? = null,

        @SerializedName("subcategory_name")
        @Expose
        var subcategoryName: String? = null,

        @SerializedName("brand_id")
        @Expose
        var brandId: String? = null,

        @SerializedName("brand_name")
        @Expose
        var brandName: String? = null,

        @SerializedName("price")
        @Expose
        var price: String? = null,

        @SerializedName("is_best_seler")
        @Expose
        var isBestSeler: String? = null,

        @SerializedName("wishlist_exist")
        @Expose
        var wishlistExist: String? = null
    )

    data class SubCategory (
        @SerializedName("subcategory_id")
        @Expose
        var subcategory_id: String? = null,

        @SerializedName("subcategory_name")
        @Expose
        var subcategory_name: String? = null,

        @SerializedName("subcategory_name_ar")
        @Expose
        var subcategory_name_ar: String? = null,

        @SerializedName("subcategory_category")
        @Expose
        var subcategory_category: String? = null,

        @SerializedName("subcategory_image")
        @Expose
        var subcategory_image: String? = null
    )
}