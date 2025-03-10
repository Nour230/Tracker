package com.example.testtracker.main_app.home.dailymeal.model;

import com.example.testtracker.db.MealLocalDataSource;
import com.example.testtracker.network.RemoteDataSource;
import com.example.testtracker.network.NetworkCallBack;

public class MealRepositoryImpl implements RemoteDataSource {
     MealLocalDataSource localDataSource;
     RemoteDataSource remoteDataSource;
     private static MealRepositoryImpl repo = null;

     private MealRepositoryImpl(MealLocalDataSource localDataSource, RemoteDataSource remoteDataSource){
         this.localDataSource = localDataSource;
         this.remoteDataSource = remoteDataSource;
     }
     public static MealRepositoryImpl getInstance(MealLocalDataSource localDataSource, RemoteDataSource remoteDataSource){
         if(repo == null){
             repo = new MealRepositoryImpl(localDataSource, remoteDataSource);
         }
         return repo;}

    @Override
    public void makeNetworkCall(NetworkCallBack networkCallBack) {
        remoteDataSource.makeNetworkCall(networkCallBack);
    }
    public void getFavMeals(){
        localDataSource.getAllMeals();
    }
    public void addMeal(Meal meal){
        localDataSource.insertMeal(meal);
    }
    public void deleteMeal(Meal meal){
        localDataSource.deleteMeal(meal);}
    public void getAllMeals(NetworkCallBack networkCallBack){
         remoteDataSource.makeNetworkCall(networkCallBack);
    }
}
