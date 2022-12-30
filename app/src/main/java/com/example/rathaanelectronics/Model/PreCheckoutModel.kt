package com.example.rathaanelectronics.Model

import java.io.Serializable

class PreCheckoutModel : Serializable{

    var area = ""
    var addressId = ""
    var deliveryType = ""
    var shippingCharge = "0"
    var coupon = ""
    var total = ""
    var grandTotal = ""
    var wallet = "0"
    var loyalty = "0"
    var orderNote = ""
    var delDate = ""
    var delTime = ""
    var pickStore = ""
    var pickStoreDetails : PickUpStoreModel.Detail?=null
    var delOn = ""
    var sameDayCharge = "0"
}