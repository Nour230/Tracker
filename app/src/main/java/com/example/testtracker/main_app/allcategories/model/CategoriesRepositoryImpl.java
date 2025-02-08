package com.example.testtracker.main_app.allcategories.model;

import com.example.testtracker.network.NetworkCallBack;
import com.example.testtracker.network.RemoteDataSource;

public class CategoriesRepositoryImpl implements RemoteDataSource {
    private static CategoriesRepositoryImpl repo = null;
    RemoteDataSource remoteDataSource;

    private CategoriesRepositoryImpl(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static CategoriesRepositoryImpl getInstance(RemoteDataSource remoteDataSource) {
        if (repo == null) {
            repo = new CategoriesRepositoryImpl(remoteDataSource);
        }
        return repo;

    }

    public void getAllCategories(NetworkCallBack networkCallBack) {
        remoteDataSource.makeNetworkCall(networkCallBack);
    }

    @Override
    public void makeNetworkCall(NetworkCallBack networkCallBack) {
        remoteDataSource.makeNetworkCall(networkCallBack);
    }
}
