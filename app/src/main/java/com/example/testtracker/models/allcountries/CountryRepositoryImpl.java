package com.example.testtracker.models.allcountries;

import android.content.Context;

import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.network.PlannerRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CountryRepositoryImpl{
    PlannerRemoteDataSource remoteDataSource = null;
    private MealLocalDataSourceImpl localDataSource =null;

    private static CountryRepositoryImpl repo = null;

    private CountryRepositoryImpl(Context context) {
        this.remoteDataSource = remoteDataSource.getInstance();
        this.localDataSource = localDataSource.getInstance(context);
    }
    public static CountryRepositoryImpl getInstance(Context context) {
        if (repo == null) {
            repo = new CountryRepositoryImpl(context);
        }
        return repo;}

    public Single<List<Country>> getAllCountries(){
        return remoteDataSource.getAllCountries()
                .map(AllCounties::getCountries);
    }
}
