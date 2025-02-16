package com.example.testtracker.models.allcategory;

import android.content.Context;

import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.network.PlannerRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CategoriesRepositoryImpl {
    private static CategoriesRepositoryImpl repo = null;
    private  PlannerRemoteDataSource remoteDataSource = null;
    private  MealLocalDataSourceImpl localDataSource =null;

    private CategoriesRepositoryImpl(Context context) {
        this.remoteDataSource = remoteDataSource.getInstance();
        this.localDataSource = localDataSource.getInstance(context);
    }
    public static CategoriesRepositoryImpl getInstance(Context context) {
        if (repo == null) {
            repo = new CategoriesRepositoryImpl(context);
        }
        return repo;}

    public Single<List<Category>> getAllCategories(){
        return remoteDataSource.getAllCategories()
                .map(AllCategories::getCategories);
    }


}
