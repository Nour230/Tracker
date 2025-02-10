package com.example.testtracker.network;

public interface RemoteDataSource {
    default void makeNetworkCall(NetworkCallBack networkCallBack){}
    default void getMealsByCategory(String category, NetworkCallBack networkCallBack){}
    default void getMealsByCountry(String country, NetworkCallBack networkCallBack){}
}
