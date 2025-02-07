package com.example.testtracker.network;

import java.util.List;

public interface NetworkCallBack {
    public void onSuccess(List<Meal> products);
    public void onFailure(String message);
}
