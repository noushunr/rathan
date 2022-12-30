package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.OrderCancelReasons

interface ReasonItemClick {
    fun onReasonClick(reasons: OrderCancelReasons.Data)
}