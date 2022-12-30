package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class AllCategoriesModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: MutableList<Datum>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    inner class Datum {
        @SerializedName("category_id")
        @Expose
        var categoryId: String? = null

        @SerializedName("category_label")
        @Expose
        var categoryLabel: String? = null

        @SerializedName("category_label_ar")
        @Expose
        var categoryLabelAr: String? = null

        @SerializedName("category_homepic")
        @Expose
        var categoryHomepic: String? = null

        @SerializedName("category_bannerpic")
        @Expose
        var categoryBannerpic: String? = null

        @SerializedName("category_icon")
        @Expose
        var categoryIcon: String? = null

        @SerializedName("category_prio")
        @Expose
        var categoryPrio: String? = null

        @SerializedName("category_home_prio")
        @Expose
        var categoryHomePrio: String? = null

        @SerializedName("category_date")
        @Expose
        var categoryDate: String? = null

        @SerializedName("isCategorySelected")
        @Expose
        var isCategorySelected: Boolean = false
    }
}