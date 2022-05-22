package com.example.rathaanelectronics.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SignInModel {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("usermail")
        @Expose
        private String usermail;
        @SerializedName("usermobile")
        @Expose
        private String usermobile;
        @SerializedName("token")
        @Expose
        private String token;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsermail() {
            return usermail;
        }

        public void setUsermail(String usermail) {
            this.usermail = usermail;
        }

        public String getUsermobile() {
            return usermobile;
        }

        public void setUsermobile(String usermobile) {
            this.usermobile = usermobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }
}