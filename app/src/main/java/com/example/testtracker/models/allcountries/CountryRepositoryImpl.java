package com.example.testtracker.models.allcountries;

import com.example.testtracker.network.PlannerRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CountryRepositoryImpl{
    PlannerRemoteDataSource remoteDataSource;
private static CountryRepositoryImpl repo = null;

    private CountryRepositoryImpl(PlannerRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }
    public static CountryRepositoryImpl getInstance(PlannerRemoteDataSource remoteDataSource) {
        if (repo == null) {
            repo = new CountryRepositoryImpl(remoteDataSource);
        }
        return repo;}

    public Single<List<Country>> getAllCountries(){
        return remoteDataSource.getAllCountries()
                .map(AllCounties::getCountries);
    }
}
