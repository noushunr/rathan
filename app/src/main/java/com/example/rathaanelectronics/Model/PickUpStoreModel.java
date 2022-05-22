package com.example.rathaanelectronics.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PickUpStoreModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Data {

        @SerializedName("details")
        @Expose
        private List<Detail> details = null;

        public List<Detail> getDetails() {
            return details;
        }

        public void setDetails(List<Detail> details) {
            this.details = details;
        }

    }

    public class Detail {

        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("store_name")
        @Expose
        private String storeName;
        @SerializedName("store_address")
        @Expose
        private String storeAddress;
        @SerializedName("store_name_ar")
        @Expose
        private String storeNameAr;
        @SerializedName("store_address_ar")
        @Expose
        private String storeAddressAr;
        @SerializedName("store_map")
        @Expose
        private String storeMap;
        @SerializedName("store_email")
        @Expose
        private String storeEmail;
        @SerializedName("store_date")
        @Expose
        private String storeDate;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getStoreNameAr() {
            return storeNameAr;
        }

        public void setStoreNameAr(String storeNameAr) {
            this.storeNameAr = storeNameAr;
        }

        public String getStoreAddressAr() {
            return storeAddressAr;
        }

        public void setStoreAddressAr(String storeAddressAr) {
            this.storeAddressAr = storeAddressAr;
        }

        public String getStoreMap() {
            return storeMap;
        }

        public void setStoreMap(String storeMap) {
            this.storeMap = storeMap;
        }

        public String getStoreEmail() {
            return storeEmail;
        }

        public void setStoreEmail(String storeEmail) {
            this.storeEmail = storeEmail;
        }

        public String getStoreDate() {
            return storeDate;
        }

        public void setStoreDate(String storeDate) {
            this.storeDate = storeDate;
        }

    }

}