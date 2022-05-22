package com.example.rathaanelectronics.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SignUpModel {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Data data;

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public class Data {

        @SerializedName("response")
        @Expose
        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

    }

    public class Response {

        @SerializedName("Sign up ")
        @Expose
        private String signUp;

        public String getSignUp() {
            return signUp;
        }

        public void setSignUp(String signUp) {
            this.signUp = signUp;
        }

    }


}