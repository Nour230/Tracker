package com.example.testtracker.models.mealdetails;

import com.example.testtracker.network.RemoteDataSource;

public class MealDetailsRepositoryImpl {
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

}
