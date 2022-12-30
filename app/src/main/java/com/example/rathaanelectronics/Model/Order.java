package com.example.rathaanelectronics.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("orders_uniq_id")
    @Expose
    private String ordersUniqId;
    @SerializedName("orders_user_id")
    @Expose
    private String ordersUserId;
    @SerializedName("orders_adrs_id")
    @Expose
    private String ordersAdrsId;
    @SerializedName("orders_paymode")
    @Expose
    private String ordersPaymode;
    @SerializedName("orders_total_amount")
    @Expose
    private String ordersTotalAmount;
    @SerializedName("orders_total_offer_amount")
    @Expose
    private String ordersTotalOfferAmount;
    @SerializedName("orders_promocode")
    @Expose
    private String ordersPromocode;
    @SerializedName("orders_total_qty")
    @Expose
    private String ordersTotalQty;
    @SerializedName("orders_delcharge")
    @Expose
    private String ordersDelcharge;
    @SerializedName("orders_same_delcharge")
    @Expose
    private String ordersSameDelcharge;
    @SerializedName("orders_invoice")
    @Expose
    private String ordersInvoice;
    @SerializedName("orders_status")
    @Expose
    private String ordersStatus;
    @SerializedName("orders_cancel_status")
    @Expose
    private String ordersCancelStatus;
    @SerializedName("orders_date")
    @Expose
    private String ordersDate;
    @SerializedName("orders_time")
    @Expose
    private String ordersTime;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("walletapplied")
    @Expose
    private String walletapplied;
    @SerializedName("loyaltyapplied")
    @Expose
    private String loyaltyapplied;

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrdersUniqId() {
        return ordersUniqId;
    }

    public void setOrdersUniqId(String ordersUniqId) {
        this.ordersUniqId = ordersUniqId;
    }

    public String getOrdersUserId() {
        return ordersUserId;
    }

    public void setOrdersUserId(String ordersUserId) {
        this.ordersUserId = ordersUserId;
    }

    public String getOrdersAdrsId() {
        return ordersAdrsId;
    }

    public void setOrdersAdrsId(String ordersAdrsId) {
        this.ordersAdrsId = ordersAdrsId;
    }

    public String getOrdersPaymode() {
        return ordersPaymode;
    }

    public void setOrdersPaymode(String ordersPaymode) {
        this.ordersPaymode = ordersPaymode;
    }

    public String getOrdersTotalAmount() {
        return ordersTotalAmount;
    }

    public void setOrdersTotalAmount(String ordersTotalAmount) {
        this.ordersTotalAmount = ordersTotalAmount;
    }

    public String getOrdersTotalOfferAmount() {
        return ordersTotalOfferAmount;
    }

    public void setOrdersTotalOfferAmount(String ordersTotalOfferAmount) {
        this.ordersTotalOfferAmount = ordersTotalOfferAmount;
    }

    public String getOrdersPromocode() {
        return ordersPromocode;
    }

    public void setOrdersPromocode(String ordersPromocode) {
        this.ordersPromocode = ordersPromocode;
    }

    public String getOrdersTotalQty() {
        return ordersTotalQty;
    }

    public void setOrdersTotalQty(String ordersTotalQty) {
        this.ordersTotalQty = ordersTotalQty;
    }

    public String getOrdersDelcharge() {
        return ordersDelcharge;
    }

    public void setOrdersDelcharge(String ordersDelcharge) {
        this.ordersDelcharge = ordersDelcharge;
    }

    public String getOrdersSameDelcharge() {
        return ordersSameDelcharge;
    }

    public void setOrdersSameDelcharge(String ordersSameDelcharge) {
        this.ordersSameDelcharge = ordersSameDelcharge;
    }

    public String getOrdersInvoice() {
        return ordersInvoice;
    }

    public void setOrdersInvoice(String ordersInvoice) {
        this.ordersInvoice = ordersInvoice;
    }

    public String getOrdersStatus() {
        return ordersStatus;
    }

    public void setOrdersStatus(String ordersStatus) {
        this.ordersStatus = ordersStatus;
    }

    public String getOrdersCancelStatus() {
        return ordersCancelStatus;
    }

    public void setOrdersCancelStatus(String ordersCancelStatus) {
        this.ordersCancelStatus = ordersCancelStatus;
    }

    public String getOrdersDate() {
        return ordersDate;
    }

    public void setOrdersDate(String ordersDate) {
        this.ordersDate = ordersDate;
    }

    public String getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTime(String ordersTime) {
        this.ordersTime = ordersTime;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getWalletapplied() {
        return walletapplied;
    }

    public void setWalletapplied(String walletapplied) {
        this.walletapplied = walletapplied;
    }

    public String getLoyaltyapplied() {
        return loyaltyapplied;
    }

    public void setLoyaltyapplied(String loyaltyapplied) {
        this.loyaltyapplied = loyaltyapplied;
    }

}