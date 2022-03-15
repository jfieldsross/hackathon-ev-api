package com.harrydulaney.firebaseauthstarter.model;

public class ApiResponse<T> {

    T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
