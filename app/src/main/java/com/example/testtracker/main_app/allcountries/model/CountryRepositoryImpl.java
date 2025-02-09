package com.example.testtracker.main_app.allcountries.model;

import com.example.testtracker.network.NetworkCallBack;
import com.example.testtracker.network.RemoteDataSource;

public class CountryRepositoryImpl implements RemoteDataSource{
    RemoteDataSource remoteDataSource;
private static CountryRepositoryImpl repo = null;

    private CountryRepositoryImpl(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }
    public static CountryRepositoryImpl getInstance(RemoteDataSource remoteDataSource) {
        if (repo == null) {
            repo = new CountryRepositoryImpl(remoteDataSource);
        }
        return repo;}


    @Override
    public void makeNetworkCall(NetworkCallBack networkCallBack) {
        remoteDataSource.makeNetworkCall(networkCallBack);
    }
    public void getAllCountries(NetworkCallBack networkCallBack){
        remoteDataSource.makeNetworkCall(networkCallBack);
    }
}
