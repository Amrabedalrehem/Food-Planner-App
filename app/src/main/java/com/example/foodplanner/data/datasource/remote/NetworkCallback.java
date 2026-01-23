package com.example.foodplanner.data.datasource.remote;

public interface NetworkCallback<T> {
    void onSuccess(T data);
    void onError(String errorMessage);
}