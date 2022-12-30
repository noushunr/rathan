package com.example.rathaanelectronics.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProfileModel implements Serializable{
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

    public class Data implements Serializable {

        @SerializedName("user_fname")
        @Expose
        private String userFirstname;
        @SerializedName("user_lname")
        @Expose
        private String userLastname;
        @SerializedName("user_mail")
        @Expose
        private String usermail;
        @SerializedName("user_displayname")
        @Expose
        private String user_displayname;
        @SerializedName("user_password")
        @Expose
        private String userPassword;
        @SerializedName("token")
        @Expose
        private String token;

        public String getUsername() {
            return userFirstname;
        }

        public void setUsername(String username) {
            this.userFirstname = username;
        }

        public String getUsermail() {
            return usermail;
        }

        public void setUsermail(String usermail) {
            this.usermail = usermail;
        }

        public String getUsermobile() {
            return user_displayname;
        }

        public void setUsermobile(String usermobile) {
            this.user_displayname = usermobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserFirstname() {
            return userFirstname;
        }

        public void setUserFirstname(String userFirstname) {
            this.userFirstname = userFirstname;
        }

        public String getUserLastname() {
            return userLastname;
        }

        public void setUserLastname(String userLastname) {
            this.userLastname = userLastname;
        }

        public String getUser_displayname() {
            return user_displayname;
        }

        public void setUser_displayname(String user_displayname) {
            this.user_displayname = user_displayname;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }
    }
}