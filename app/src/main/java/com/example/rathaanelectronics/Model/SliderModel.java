package com.example.rathaanelectronics.Model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Datum {

        @SerializedName("banner_id")
        @Expose
        private String bannerId;
        @SerializedName("banner_title")
        @Expose
        private String bannerTitle;
        @SerializedName("banner_shortdesc")
        @Expose
        private String bannerShortdesc;
        @SerializedName("banner_titlear")
        @Expose
        private String bannerTitlear;
        @SerializedName("banner_shortdescar")
        @Expose
        private String bannerShortdescar;
        @SerializedName("banner_prio")
        @Expose
        private String bannerPrio;
        @SerializedName("banner_url")
        @Expose
        private String bannerUrl;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;
        @SerializedName("banner_date")
        @Expose
        private String bannerDate;

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
        }

        public String getBannerTitle() {
            return bannerTitle;
        }

        public void setBannerTitle(String bannerTitle) {
            this.bannerTitle = bannerTitle;
        }

        public String getBannerShortdesc() {
            return bannerShortdesc;
        }

        public void setBannerShortdesc(String bannerShortdesc) {
            this.bannerShortdesc = bannerShortdesc;
        }

        public String getBannerTitlear() {
            return bannerTitlear;
        }

        public void setBannerTitlear(String bannerTitlear) {
            this.bannerTitlear = bannerTitlear;
        }

        public String getBannerShortdescar() {
            return bannerShortdescar;
        }

        public void setBannerShortdescar(String bannerShortdescar) {
            this.bannerShortdescar = bannerShortdescar;
        }

        public String getBannerPrio() {
            return bannerPrio;
        }

        public void setBannerPrio(String bannerPrio) {
            this.bannerPrio = bannerPrio;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        public String getBannerDate() {
            return bannerDate;
        }

        public void setBannerDate(String bannerDate) {
            this.bannerDate = bannerDate;
        }

    }

}