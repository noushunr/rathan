package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.AddressResponseModel

interface AddressItemClick {
    fun editAddressItem(addressItem: AddressResponseModel.Details)
    fun deleteAddressItem(addressId: String)
}