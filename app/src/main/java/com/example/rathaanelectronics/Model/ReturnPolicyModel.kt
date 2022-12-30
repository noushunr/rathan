package com.example.rathaanelectronics.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.rathaanelectronics.Model.DealsModel.Datum

class ReturnPolicyModel {


    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    data class Data(

        @SerializedName("returnpolicy_id")
        @Expose
        var returnPolicyId: String? = null,

        @SerializedName("returnpolicy_content")
        @Expose
        var returnPolicyContent: String? = null,

        @SerializedName("returnpolicy_content_ar")
        @Expose
        var returnPolicyContent_ar: String? = null,

        @SerializedName("returnpolicy_date")
        @Expose
        var returnPolicyDate: String? = null

        )

}