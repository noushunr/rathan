package com.example.rathaanelectronics.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddToCompareResponseModel {
    @SerializedName("status")
    @Expose
    var status: Boolean = false

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data{
        @SerializedName("total_compare_list_count")
        @Expose
        var totalItemsInList: Int? = null

        @SerializedName("flag")
        @Expose
        var flag: Int? = null
    }
}