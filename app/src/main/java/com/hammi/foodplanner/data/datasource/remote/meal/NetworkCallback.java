package com.hammi.foodplanner.data.datasource.remote.meal;

public interface NetworkCallback<T> {
    void onSuccess(T data);
    void onError(String errorMessage);
}