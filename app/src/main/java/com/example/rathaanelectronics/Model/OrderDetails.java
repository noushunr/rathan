package com.example.rathaanelectronics.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetails implements Serializable {

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
    @SerializedName("dc_id")
    @Expose
    private String dcId;
    @SerializedName("dc_cart_id")
    @Expose
    private String dcCartId;
    @SerializedName("dc_user_id")
    @Expose
    private String dcUserId;
    @SerializedName("dc_prod_id")
    @Expose
    private String dcProdId;
    @SerializedName("dc_order_id")
    @Expose
    private String dcOrderId;
    @SerializedName("dc_agent_id")
    @Expose
    private String dcAgentId;
    @SerializedName("dc_address_id")
    @Expose
    private String dcAddressId;
    @SerializedName("dc_prod_name")
    @Expose
    private String dcProdName;
    @SerializedName("dc_prod_desc")
    @Expose
    private String dcProdDesc;
    @SerializedName("dc_prod_measure")
    @Expose
    private String dcProdMeasure;
    @SerializedName("dc_prod_quantity")
    @Expose
    private String dcProdQuantity;
    @SerializedName("dc_prod_image")
    @Expose
    private String dcProdImage;
    @SerializedName("dc_prod_size")
    @Expose
    private String dcProdSize;
    @SerializedName("dc_prod_age")
    @Expose
    private Object dcProdAge;
    @SerializedName("dc_prod_color")
    @Expose
    private String dcProdColor;
    @SerializedName("dc_prod_commoffer")
    @Expose
    private String dcProdCommoffer;
    @SerializedName("dc_prod_tax")
    @Expose
    private String dcProdTax;
    @SerializedName("dc_prod_actualcommission")
    @Expose
    private String dcProdActualcommission;
    @SerializedName("dc_prod_actualstoreprice")
    @Expose
    private String dcProdActualstoreprice;
    @SerializedName("dc_time")
    @Expose
    private String dcTime;
    @SerializedName("dc_date")
    @Expose
    private String dcDate;
    @SerializedName("dc_shipped_date")
    @Expose
    private String dcShippedDate;
    @SerializedName("dc_shipped_time")
    @Expose
    private String dcShippedTime;
    @SerializedName("dc_delivery_date")
    @Expose
    private String dcDeliveryDate;
    @SerializedName("dc_delivery_time")
    @Expose
    private String dcDeliveryTime;
    @SerializedName("dc_status")
    @Expose
    private String dcStatus;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("dc_cancel_order")
    @Expose
    private String dcCancelOrder;
    @SerializedName("dc_delivery_distance")
    @Expose
    private String dcDeliveryDistance;
    @SerializedName("dc_deliveryboy_charge")
    @Expose
    private String dcDeliveryboyCharge;
    @SerializedName("dc_deliveryowner_charge")
    @Expose
    private String dcDeliveryownerCharge;
    @SerializedName("dc_payment_mode")
    @Expose
    private String dcPaymentMode;
    @SerializedName("dc_prod_giftstat")
    @Expose
    private String dcProdGiftstat;
    @SerializedName("dc_prod_giftdate")
    @Expose
    private String dcProdGiftdate;
    @SerializedName("dc_prod_gifttime")
    @Expose
    private String dcProdGifttime;
    @SerializedName("dc_prod_giftmsg")
    @Expose
    private String dcProdGiftmsg;
    @SerializedName("dc_prod_giftamount")
    @Expose
    private String dcProdGiftamount;
    @SerializedName("dc_prod_bundlestat")
    @Expose
    private String dcProdBundlestat;
    @SerializedName("dc_prod_bndle_offerid")
    @Expose
    private String dcProdBndleOfferid;
    @SerializedName("dc_prod_bndle_prodids")
    @Expose
    private String dcProdBndleProdids;
    @SerializedName("dc_prod_install_fee")
    @Expose
    private String dcProdInstallFee;
    @SerializedName("dc_prod_mrp")
    @Expose
    private String dcProdMrp;
    @SerializedName("dc_prod_loyalty")
    @Expose
    private String dcProdLoyalty;
    @SerializedName("addressprofile_id")
    @Expose
    private String addressprofileId;
    @SerializedName("addressprofile_userid")
    @Expose
    private String addressprofileUserid;
    @SerializedName("addressprofile_fname")
    @Expose
    private String addressprofileFname;
    @SerializedName("addressprofile_lname")
    @Expose
    private String addressprofileLname;
    @SerializedName("addressprofile_mail")
    @Expose
    private String addressprofileMail;
    @SerializedName("addressprofile_mobile")
    @Expose
    private String addressprofileMobile;
    @SerializedName("addressprofile_country")
    @Expose
    private String addressprofileCountry;
    @SerializedName("addressprofile_city")
    @Expose
    private String addressprofileCity;
    @SerializedName("addressprofile_street")
    @Expose
    private String addressprofileStreet;
    @SerializedName("addressprofile_block")
    @Expose
    private String addressprofileBlock;
    @SerializedName("addressprofile_hb")
    @Expose
    private String addressprofileHb;
    @SerializedName("addressprofile_avenue")
    @Expose
    private String addressprofileAvenue;
    @SerializedName("addressprofile_more")
    @Expose
    private Object addressprofileMore;
    @SerializedName("addressprofile_date")
    @Expose
    private String addressprofileDate;
    @SerializedName("addressprofile_inv_status")
    @Expose
    private String addressprofileInvStatus;
    @SerializedName("addressprofile_inv_fname")
    @Expose
    private String addressprofileInvFname;
    @SerializedName("addressprofile_inv_lname")
    @Expose
    private String addressprofileInvLname;
    @SerializedName("addressprofile_inv_mail")
    @Expose
    private String addressprofileInvMail;
    @SerializedName("addressprofile_inv_mobile")
    @Expose
    private String addressprofileInvMobile;
    @SerializedName("addressprofile_inv_country")
    @Expose
    private String addressprofileInvCountry;
    @SerializedName("addressprofile_inv_city")
    @Expose
    private String addressprofileInvCity;
    @SerializedName("addressprofile_inv_street")
    @Expose
    private String addressprofileInvStreet;
    @SerializedName("addressprofile_inv_block")
    @Expose
    private String addressprofileInvBlock;
    @SerializedName("addressprofile_inv_hb")
    @Expose
    private String addressprofileInvHb;
    @SerializedName("addressprofile_inv_avenue")
    @Expose
    private String addressprofileInvAvenue;
    @SerializedName("addressprofile_inv_more")
    @Expose
    private String addressprofileInvMore;
    @SerializedName("addressprofile_mobile2")
    @Expose
    private String addressprofileMobile2;
    @SerializedName("addressprofile_inv_mobile2")
    @Expose
    private String addressprofileInvMobile2;
    @SerializedName("addressprofile_handovertype")
    @Expose
    private String addressprofileHandovertype;
    @SerializedName("addressprofile_pickstore")
    @Expose
    private Object addressprofilePickstore;
    @SerializedName("addressprofile_dalivryon")
    @Expose
    private String addressprofileDalivryon;
    @SerializedName("addressprofile_deldate")
    @Expose
    private String addressprofileDeldate;
    @SerializedName("addressprofile_deltime")
    @Expose
    private String addressprofileDeltime;
    @SerializedName("addressprofile_delexact_time")
    @Expose
    private String addressprofileDelexactTime;
    @SerializedName("addressprofile_govr")
    @Expose
    private String addressprofileGovr;
    @SerializedName("addressprofile_inv_govr")
    @Expose
    private String addressprofileInvGovr;
    @SerializedName("cancelling_product")
    @Expose
    private String cancellingProduct;

    @SerializedName("product_name_arab")
    @Expose
    private String productNameArab;


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

    public String getDcId() {
        return dcId;
    }

    public void setDcId(String dcId) {
        this.dcId = dcId;
    }

    public String getDcCartId() {
        return dcCartId;
    }

    public void setDcCartId(String dcCartId) {
        this.dcCartId = dcCartId;
    }

    public String getDcUserId() {
        return dcUserId;
    }

    public void setDcUserId(String dcUserId) {
        this.dcUserId = dcUserId;
    }

    public String getDcProdId() {
        return dcProdId;
    }

    public void setDcProdId(String dcProdId) {
        this.dcProdId = dcProdId;
    }

    public String getDcOrderId() {
        return dcOrderId;
    }

    public void setDcOrderId(String dcOrderId) {
        this.dcOrderId = dcOrderId;
    }

    public String getDcAgentId() {
        return dcAgentId;
    }

    public void setDcAgentId(String dcAgentId) {
        this.dcAgentId = dcAgentId;
    }

    public String getDcAddressId() {
        return dcAddressId;
    }

    public void setDcAddressId(String dcAddressId) {
        this.dcAddressId = dcAddressId;
    }

    public String getDcProdName() {
        return dcProdName;
    }

    public void setDcProdName(String dcProdName) {
        this.dcProdName = dcProdName;
    }

    public String getDcProdDesc() {
        return dcProdDesc;
    }

    public void setDcProdDesc(String dcProdDesc) {
        this.dcProdDesc = dcProdDesc;
    }

    public String getDcProdMeasure() {
        return dcProdMeasure;
    }

    public void setDcProdMeasure(String dcProdMeasure) {
        this.dcProdMeasure = dcProdMeasure;
    }

    public String getDcProdQuantity() {
        return dcProdQuantity;
    }

    public void setDcProdQuantity(String dcProdQuantity) {
        this.dcProdQuantity = dcProdQuantity;
    }

    public String getDcProdImage() {
        return dcProdImage;
    }

    public void setDcProdImage(String dcProdImage) {
        this.dcProdImage = dcProdImage;
    }

    public String getDcProdSize() {
        return dcProdSize;
    }

    public void setDcProdSize(String dcProdSize) {
        this.dcProdSize = dcProdSize;
    }

    public Object getDcProdAge() {
        return dcProdAge;
    }

    public void setDcProdAge(Object dcProdAge) {
        this.dcProdAge = dcProdAge;
    }

    public String getDcProdColor() {
        return dcProdColor;
    }

    public void setDcProdColor(String dcProdColor) {
        this.dcProdColor = dcProdColor;
    }

    public String getDcProdCommoffer() {
        return dcProdCommoffer;
    }

    public void setDcProdCommoffer(String dcProdCommoffer) {
        this.dcProdCommoffer = dcProdCommoffer;
    }

    public String getDcProdTax() {
        return dcProdTax;
    }

    public void setDcProdTax(String dcProdTax) {
        this.dcProdTax = dcProdTax;
    }

    public String getDcProdActualcommission() {
        return dcProdActualcommission;
    }

    public void setDcProdActualcommission(String dcProdActualcommission) {
        this.dcProdActualcommission = dcProdActualcommission;
    }

    public String getDcProdActualstoreprice() {
        return dcProdActualstoreprice;
    }

    public void setDcProdActualstoreprice(String dcProdActualstoreprice) {
        this.dcProdActualstoreprice = dcProdActualstoreprice;
    }

    public String getDcTime() {
        return dcTime;
    }

    public void setDcTime(String dcTime) {
        this.dcTime = dcTime;
    }

    public String getDcDate() {
        return dcDate;
    }

    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }

    public String getDcShippedDate() {
        return dcShippedDate;
    }

    public void setDcShippedDate(String dcShippedDate) {
        this.dcShippedDate = dcShippedDate;
    }

    public String getDcShippedTime() {
        return dcShippedTime;
    }

    public void setDcShippedTime(String dcShippedTime) {
        this.dcShippedTime = dcShippedTime;
    }

    public String getDcDeliveryDate() {
        return dcDeliveryDate;
    }

    public void setDcDeliveryDate(String dcDeliveryDate) {
        this.dcDeliveryDate = dcDeliveryDate;
    }

    public String getDcDeliveryTime() {
        return dcDeliveryTime;
    }

    public void setDcDeliveryTime(String dcDeliveryTime) {
        this.dcDeliveryTime = dcDeliveryTime;
    }

    public String getDcStatus() {
        return dcStatus;
    }

    public void setDcStatus(String dcStatus) {
        this.dcStatus = dcStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDcCancelOrder() {
        return dcCancelOrder;
    }

    public void setDcCancelOrder(String dcCancelOrder) {
        this.dcCancelOrder = dcCancelOrder;
    }

    public String getDcDeliveryDistance() {
        return dcDeliveryDistance;
    }

    public void setDcDeliveryDistance(String dcDeliveryDistance) {
        this.dcDeliveryDistance = dcDeliveryDistance;
    }

    public String getDcDeliveryboyCharge() {
        return dcDeliveryboyCharge;
    }

    public void setDcDeliveryboyCharge(String dcDeliveryboyCharge) {
        this.dcDeliveryboyCharge = dcDeliveryboyCharge;
    }

    public String getDcDeliveryownerCharge() {
        return dcDeliveryownerCharge;
    }

    public void setDcDeliveryownerCharge(String dcDeliveryownerCharge) {
        this.dcDeliveryownerCharge = dcDeliveryownerCharge;
    }

    public String getDcPaymentMode() {
        return dcPaymentMode;
    }

    public void setDcPaymentMode(String dcPaymentMode) {
        this.dcPaymentMode = dcPaymentMode;
    }

    public String getDcProdGiftstat() {
        return dcProdGiftstat;
    }

    public void setDcProdGiftstat(String dcProdGiftstat) {
        this.dcProdGiftstat = dcProdGiftstat;
    }

    public String getDcProdGiftdate() {
        return dcProdGiftdate;
    }

    public void setDcProdGiftdate(String dcProdGiftdate) {
        this.dcProdGiftdate = dcProdGiftdate;
    }

    public String getDcProdGifttime() {
        return dcProdGifttime;
    }

    public void setDcProdGifttime(String dcProdGifttime) {
        this.dcProdGifttime = dcProdGifttime;
    }

    public String getDcProdGiftmsg() {
        return dcProdGiftmsg;
    }

    public void setDcProdGiftmsg(String dcProdGiftmsg) {
        this.dcProdGiftmsg = dcProdGiftmsg;
    }

    public String getDcProdGiftamount() {
        return dcProdGiftamount;
    }

    public void setDcProdGiftamount(String dcProdGiftamount) {
        this.dcProdGiftamount = dcProdGiftamount;
    }

    public String getDcProdBundlestat() {
        return dcProdBundlestat;
    }

    public void setDcProdBundlestat(String dcProdBundlestat) {
        this.dcProdBundlestat = dcProdBundlestat;
    }

    public String getDcProdBndleOfferid() {
        return dcProdBndleOfferid;
    }

    public void setDcProdBndleOfferid(String dcProdBndleOfferid) {
        this.dcProdBndleOfferid = dcProdBndleOfferid;
    }

    public String getDcProdBndleProdids() {
        return dcProdBndleProdids;
    }

    public void setDcProdBndleProdids(String dcProdBndleProdids) {
        this.dcProdBndleProdids = dcProdBndleProdids;
    }

    public String getDcProdInstallFee() {
        return dcProdInstallFee;
    }

    public void setDcProdInstallFee(String dcProdInstallFee) {
        this.dcProdInstallFee = dcProdInstallFee;
    }

    public String getDcProdMrp() {
        return dcProdMrp;
    }

    public void setDcProdMrp(String dcProdMrp) {
        this.dcProdMrp = dcProdMrp;
    }

    public String getDcProdLoyalty() {
        return dcProdLoyalty;
    }

    public void setDcProdLoyalty(String dcProdLoyalty) {
        this.dcProdLoyalty = dcProdLoyalty;
    }

    public String getAddressprofileId() {
        return addressprofileId;
    }

    public void setAddressprofileId(String addressprofileId) {
        this.addressprofileId = addressprofileId;
    }

    public String getAddressprofileUserid() {
        return addressprofileUserid;
    }

    public void setAddressprofileUserid(String addressprofileUserid) {
        this.addressprofileUserid = addressprofileUserid;
    }

    public String getAddressprofileFname() {
        return addressprofileFname;
    }

    public void setAddressprofileFname(String addressprofileFname) {
        this.addressprofileFname = addressprofileFname;
    }

    public String getAddressprofileLname() {
        return addressprofileLname;
    }

    public void setAddressprofileLname(String addressprofileLname) {
        this.addressprofileLname = addressprofileLname;
    }

    public String getAddressprofileMail() {
        return addressprofileMail;
    }

    public void setAddressprofileMail(String addressprofileMail) {
        this.addressprofileMail = addressprofileMail;
    }

    public String getAddressprofileMobile() {
        return addressprofileMobile;
    }

    public void setAddressprofileMobile(String addressprofileMobile) {
        this.addressprofileMobile = addressprofileMobile;
    }

    public String getAddressprofileCountry() {
        return addressprofileCountry;
    }

    public void setAddressprofileCountry(String addressprofileCountry) {
        this.addressprofileCountry = addressprofileCountry;
    }

    public String getAddressprofileCity() {
        return addressprofileCity;
    }

    public void setAddressprofileCity(String addressprofileCity) {
        this.addressprofileCity = addressprofileCity;
    }

    public String getAddressprofileStreet() {
        return addressprofileStreet;
    }

    public void setAddressprofileStreet(String addressprofileStreet) {
        this.addressprofileStreet = addressprofileStreet;
    }

    public String getAddressprofileBlock() {
        return addressprofileBlock;
    }

    public void setAddressprofileBlock(String addressprofileBlock) {
        this.addressprofileBlock = addressprofileBlock;
    }

    public String getAddressprofileHb() {
        return addressprofileHb;
    }

    public void setAddressprofileHb(String addressprofileHb) {
        this.addressprofileHb = addressprofileHb;
    }

    public String getAddressprofileAvenue() {
        return addressprofileAvenue;
    }

    public void setAddressprofileAvenue(String addressprofileAvenue) {
        this.addressprofileAvenue = addressprofileAvenue;
    }

    public Object getAddressprofileMore() {
        return addressprofileMore;
    }

    public void setAddressprofileMore(Object addressprofileMore) {
        this.addressprofileMore = addressprofileMore;
    }

    public String getAddressprofileDate() {
        return addressprofileDate;
    }

    public void setAddressprofileDate(String addressprofileDate) {
        this.addressprofileDate = addressprofileDate;
    }

    public String getAddressprofileInvStatus() {
        return addressprofileInvStatus;
    }

    public void setAddressprofileInvStatus(String addressprofileInvStatus) {
        this.addressprofileInvStatus = addressprofileInvStatus;
    }

    public String getAddressprofileInvFname() {
        return addressprofileInvFname;
    }

    public void setAddressprofileInvFname(String addressprofileInvFname) {
        this.addressprofileInvFname = addressprofileInvFname;
    }

    public String getAddressprofileInvLname() {
        return addressprofileInvLname;
    }

    public void setAddressprofileInvLname(String addressprofileInvLname) {
        this.addressprofileInvLname = addressprofileInvLname;
    }

    public String getAddressprofileInvMail() {
        return addressprofileInvMail;
    }

    public void setAddressprofileInvMail(String addressprofileInvMail) {
        this.addressprofileInvMail = addressprofileInvMail;
    }

    public String getAddressprofileInvMobile() {
        return addressprofileInvMobile;
    }

    public void setAddressprofileInvMobile(String addressprofileInvMobile) {
        this.addressprofileInvMobile = addressprofileInvMobile;
    }

    public String getAddressprofileInvCountry() {
        return addressprofileInvCountry;
    }

    public void setAddressprofileInvCountry(String addressprofileInvCountry) {
        this.addressprofileInvCountry = addressprofileInvCountry;
    }

    public String getAddressprofileInvCity() {
        return addressprofileInvCity;
    }

    public void setAddressprofileInvCity(String addressprofileInvCity) {
        this.addressprofileInvCity = addressprofileInvCity;
    }

    public String getAddressprofileInvStreet() {
        return addressprofileInvStreet;
    }

    public void setAddressprofileInvStreet(String addressprofileInvStreet) {
        this.addressprofileInvStreet = addressprofileInvStreet;
    }

    public String getAddressprofileInvBlock() {
        return addressprofileInvBlock;
    }

    public void setAddressprofileInvBlock(String addressprofileInvBlock) {
        this.addressprofileInvBlock = addressprofileInvBlock;
    }

    public String getAddressprofileInvHb() {
        return addressprofileInvHb;
    }

    public void setAddressprofileInvHb(String addressprofileInvHb) {
        this.addressprofileInvHb = addressprofileInvHb;
    }

    public String getAddressprofileInvAvenue() {
        return addressprofileInvAvenue;
    }

    public void setAddressprofileInvAvenue(String addressprofileInvAvenue) {
        this.addressprofileInvAvenue = addressprofileInvAvenue;
    }

    public String getAddressprofileInvMore() {
        return addressprofileInvMore;
    }

    public void setAddressprofileInvMore(String addressprofileInvMore) {
        this.addressprofileInvMore = addressprofileInvMore;
    }

    public String getAddressprofileMobile2() {
        return addressprofileMobile2;
    }

    public void setAddressprofileMobile2(String addressprofileMobile2) {
        this.addressprofileMobile2 = addressprofileMobile2;
    }

    public String getAddressprofileInvMobile2() {
        return addressprofileInvMobile2;
    }

    public void setAddressprofileInvMobile2(String addressprofileInvMobile2) {
        this.addressprofileInvMobile2 = addressprofileInvMobile2;
    }

    public String getAddressprofileHandovertype() {
        return addressprofileHandovertype;
    }

    public void setAddressprofileHandovertype(String addressprofileHandovertype) {
        this.addressprofileHandovertype = addressprofileHandovertype;
    }

    public Object getAddressprofilePickstore() {
        return addressprofilePickstore;
    }

    public void setAddressprofilePickstore(Object addressprofilePickstore) {
        this.addressprofilePickstore = addressprofilePickstore;
    }

    public String getAddressprofileDalivryon() {
        return addressprofileDalivryon;
    }

    public void setAddressprofileDalivryon(String addressprofileDalivryon) {
        this.addressprofileDalivryon = addressprofileDalivryon;
    }

    public String getAddressprofileDeldate() {
        return addressprofileDeldate;
    }

    public void setAddressprofileDeldate(String addressprofileDeldate) {
        this.addressprofileDeldate = addressprofileDeldate;
    }

    public String getAddressprofileDeltime() {
        return addressprofileDeltime;
    }

    public void setAddressprofileDeltime(String addressprofileDeltime) {
        this.addressprofileDeltime = addressprofileDeltime;
    }

    public String getAddressprofileDelexactTime() {
        return addressprofileDelexactTime;
    }

    public void setAddressprofileDelexactTime(String addressprofileDelexactTime) {
        this.addressprofileDelexactTime = addressprofileDelexactTime;
    }

    public String getAddressprofileGovr() {
        return addressprofileGovr;
    }

    public void setAddressprofileGovr(String addressprofileGovr) {
        this.addressprofileGovr = addressprofileGovr;
    }

    public String getAddressprofileInvGovr() {
        return addressprofileInvGovr;
    }

    public void setAddressprofileInvGovr(String addressprofileInvGovr) {
        this.addressprofileInvGovr = addressprofileInvGovr;
    }

    public String getCancellingProduct() {
        return cancellingProduct;
    }

    public void setCancellingProduct(String cancellingProduct) {
        this.cancellingProduct = cancellingProduct;
    }

    public String getProductNameArab() {
        return productNameArab;
    }

    public void setProductNameArab(String productNameArab) {
        this.productNameArab = productNameArab;
    }
}

