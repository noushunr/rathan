package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class BestDealCategory {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

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


    }
}