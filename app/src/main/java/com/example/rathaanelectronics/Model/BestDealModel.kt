package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class BestDealModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<Product>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    inner class Datum {
        @SerializedName("product_id")
        @Expose
        var productId: String? = null

        @SerializedName("product_unique_id")
        @Expose
        var productUniqueId: String? = null

        @SerializedName("prod_featured")
        @Expose
        var prodFeatured: String? = null

        @SerializedName("prod_featured_prio")
        @Expose
        var prodFeaturedPrio: String? = null

        @SerializedName("prod_newarrive")
        @Expose
        var prodNewarrive: String? = null

        @SerializedName("prod_newarrive_prio")
        @Expose
        var prodNewarrivePrio: String? = null

        @SerializedName("prod_topselling")
        @Expose
        var prodTopselling: String? = null

        @SerializedName("prod_toprated")
        @Expose
        var prodToprated: String? = null

        @SerializedName("product_category")
        @Expose
        var productCategory: String? = null

        @SerializedName("product_subcategory")
        @Expose
        var productSubcategory: String? = null

        @SerializedName("product_division")
        @Expose
        var productDivision: String? = null

        @SerializedName("product_type")
        @Expose
        var productType: String? = null

        @SerializedName("product_brand")
        @Expose
        var productBrand: String? = null

        @SerializedName("product_priority")
        @Expose
        var productPriority: String? = null

        @SerializedName("product_size_status")
        @Expose
        var productSizeStatus: String? = null

        @SerializedName("product_name")
        @Expose
        var productName: String? = null

        @SerializedName("product_name_arab")
        @Expose
        var productNameArab: String? = null

        @SerializedName("product_rate")
        @Expose
        var productRate: String? = null

        @SerializedName("product_purchase_rate")
        @Expose
        var productPurchaseRate: String? = null

        @SerializedName("product_discount")
        @Expose
        var productDiscount: String? = null

        @SerializedName("product_discount_price")
        @Expose
        var productDiscountPrice: String? = null

        @SerializedName("product_sell_price")
        @Expose
        var productSellPrice: String? = null

        @SerializedName("product_single_quantity")
        @Expose
        var productSingleQuantity: String? = null

        @SerializedName("product_desc")
        @Expose
        var productDesc: String? = null

        @SerializedName("product_desc_arab")
        @Expose
        var productDescArab: String? = null

        @SerializedName("product_featured")
        @Expose
        var productFeatured: String? = null

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

        @SerializedName("product_shipping")
        @Expose
        var productShipping: Any? = null

        @SerializedName("product_shipping_arab")
        @Expose
        var productShippingArab: Any? = null

        @SerializedName("product_image")
        @Expose
        var productImage: String? = null

        @SerializedName("product_image_details")
        @Expose
        var productImageDetails: String? = null

        @SerializedName("product_sizeno")
        @Expose
        var productSizeno: String? = null

        @SerializedName("product_sizeletter")
        @Expose
        var productSizeletter: String? = null

        @SerializedName("product_shoesizeno")
        @Expose
        var productShoesizeno: String? = null

        @SerializedName("product_quantity")
        @Expose
        var productQuantity: String? = null

        @SerializedName("product_mrpwise")
        @Expose
        var productMrpwise: String? = null

        @SerializedName("product_sellpricewise")
        @Expose
        var productSellpricewise: String? = null

        @SerializedName("product_discountwise")
        @Expose
        var productDiscountwise: String? = null

        @SerializedName("product_available")
        @Expose
        var productAvailable: String? = null

        @SerializedName("product_age")
        @Expose
        var productAge: String? = null

        @SerializedName("product_color")
        @Expose
        var productColor: String? = null

        @SerializedName("product_status")
        @Expose
        var productStatus: String? = null

        @SerializedName("product_coming_stat")
        @Expose
        var productComingStat: Any? = null

        @SerializedName("product_coming_release_stat")
        @Expose
        var productComingReleaseStat: Any? = null

        @SerializedName("product_coming_date")
        @Expose
        var productComingDate: Any? = null

        @SerializedName("product_coming_pic")
        @Expose
        var productComingPic: String? = null

        @SerializedName("product_rating")
        @Expose
        var productRating: String? = null

        @SerializedName("product_sku")
        @Expose
        var productSku: String? = null

        @SerializedName("product_attribute")
        @Expose
        var productAttribute: String? = null

        @SerializedName("product_tags")
        @Expose
        var productTags: String? = null

        @SerializedName("product_date")
        @Expose
        var productDate: String? = null

        @SerializedName("product_offer_stat")
        @Expose
        var productOfferStat: String? = null

        @SerializedName("product_spoffer_stat")
        @Expose
        var productSpofferStat: String? = null

        @SerializedName("product_spoffer_start")
        @Expose
        var productSpofferStart: Any? = null

        @SerializedName("product_spoffer_end")
        @Expose
        var productSpofferEnd: Any? = null

        @SerializedName("product_spoffer_price")
        @Expose
        var productSpofferPrice: String? = null

        @SerializedName("product_spoffer_oldprice_show")
        @Expose
        var productSpofferOldpriceShow: String? = null

        @SerializedName("product_delivery_or_pickup")
        @Expose
        var productDeliveryOrPickup: String? = null

        @SerializedName("product_pickupstore")
        @Expose
        var productPickupstore: String? = null

        @SerializedName("prod_frond_img")
        @Expose
        var prodFrondImg: String? = null

        @SerializedName("product_deliveryon")
        @Expose
        var productDeliveryon: String? = null

        @SerializedName("product_installation")
        @Expose
        var productInstallation: String? = null

        @SerializedName("product_attributes")
        @Expose
        var productAttributes: String? = null

        @SerializedName("subcategory_id")
        @Expose
        var subcategoryId: String? = null

        @SerializedName("subcategory_name")
        @Expose
        var subcategoryName: String? = null

        @SerializedName("subcategory_name_ar")
        @Expose
        var subcategoryNameAr: String? = null

        @SerializedName("subcategory_category")
        @Expose
        var subcategoryCategory: String? = null

        @SerializedName("subcategory_image")
        @Expose
        var subcategoryImage: String? = null

        @SerializedName("subcategory_date")
        @Expose
        var subcategoryDate: String? = null

        @SerializedName("order_field")
        @Expose
        var orderField: Any? = null

        @SerializedName("hot_price")
        @Expose
        var hotPrice: String? = null

        @SerializedName("wishlist_exist")
        @Expose
        var wishlistExist: Int? = 0

        @SerializedName("brand_name")
        @Expose
        var brandName: String? = null

        @SerializedName("sameday_delivery")
        @Expose
        var samedayDelivery: String? = null

    }


}