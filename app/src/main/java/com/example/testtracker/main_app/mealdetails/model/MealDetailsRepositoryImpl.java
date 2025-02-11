package com.example.testtracker.main_app.mealdetails.model;

import com.example.testtracker.network.NetworkCallBack;
import com.example.testtracker.network.RemoteDataSource;

public class MealDetailsRepositoryImpl implements RemoteDataSource {
    RemoteDataSource remoteDataSource;
    private static MealDetailsRepositoryImpl repo = null;
    private MealDetailsRepositoryImpl(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }
    public static MealDetailsRepositoryImpl getInstance(RemoteDataSource remoteDataSource){
        if(repo == null){
            repo = new MealDetailsRepositoryImpl(remoteDataSource);
        }
        return repo;}
    @Override
    public void makeNetworkCall(NetworkCallBack networkCallBack) {
        remoteDataSource.makeNetworkCall(networkCallBack);
    }
    public void getMealDetails(String id,NetworkCallBack networkCallBack){
        remoteDataSource.getMealDetails(id,networkCallBack);
    }
}
