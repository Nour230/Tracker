package com.example.testtracker.models.allcategory;

import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.network.RemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CategoriesRepositoryImpl {
    private static CategoriesRepositoryImpl repo = null;
    private final PlannerRemoteDataSource remoteDataSource;

    private CategoriesRepositoryImpl(PlannerRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static CategoriesRepositoryImpl getInstance(PlannerRemoteDataSource remoteDataSource) {
        if (repo == null) {
            repo = new CategoriesRepositoryImpl(remoteDataSource);
        }
        return repo;

    }

    public Single<List<Category>> getAllCategories(){
        return remoteDataSource.getAllCategories()
                .map(AllCategories::getCategories);
    }


}
