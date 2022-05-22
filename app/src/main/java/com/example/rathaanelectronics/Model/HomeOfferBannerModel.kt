package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class HomeOfferBannerModel {
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
        @SerializedName("homebanner_id")
        @Expose
        var homeBannerId: String? = null

        @SerializedName("homebanner_title")
        @Expose
        var homeBannerTitle: String? = null

        @SerializedName("homebanner_title_arab")
        @Expose
        var homeBannerTitleArab: String? = null

        @SerializedName("homebanner_cat_lev")
        @Expose
        var homeBannerCatLev: String? = null

        @SerializedName("homebanner_level_id")
        @Expose
        var homeBannerLevelId: String? = null

        @SerializedName("homebanner_prio")
        @Expose
        var homeBannerPrio: String? = null

        @SerializedName("homebannerurl")
        @Expose
        var homeBannerUrl: String? = null

        @SerializedName("homebanner_image")
        @Expose
        var homeBannerImage: String? = null

        @SerializedName("homebanner_date")
        @Expose
        var homeBannerDate: String? = null
    }
}