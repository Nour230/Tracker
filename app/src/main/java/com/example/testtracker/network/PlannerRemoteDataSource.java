package com.example.testtracker.network;

import android.util.Log;

import com.example.testtracker.models.allcategory.AllCategories;
import com.example.testtracker.models.allcountries.AllCounties;
import com.example.testtracker.models.catandcountrymeals.CategoryAllMeals;
import com.example.testtracker.models.dailymeal.AllMeals;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.models.mealdetails.MealDetails;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlannerRemoteDataSource implements RemoteDataSource {
    private static final String TAG = "productClient";
    private static final String Base_url = "https://www.themealdb.com/api/json/v1/1/";
    private static PlannerRemoteDataSource client = null;
    private final AllNetWorkService mealservice;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private PlannerRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealservice = retrofit.create(AllNetWorkService.class);
    }

    public static PlannerRemoteDataSource getInstance() {
        if (client == null) {
            client = new PlannerRemoteDataSource();
        }
        return client;
    }

    public Single<AllMeals> getAllMeals() {
        return mealservice.getAllMeals();
    }

    public Single<MealDetails> getMealDetails(String id) {
        return mealservice.getMealDetails(id);
    }

    public Single<AllCategories> getAllCategories() {
        return mealservice.getAllCategories();
    }

    public Single<AllCounties> getAllCountries() {
        return mealservice.getAllCountries();
    }

    public Single<AllIngrediants> getAllIngrediants() {
        return mealservice.getAllIngredients();
    }

    public Single<CategoryAllMeals>getMealsByCategory(String category){
        return mealservice.getMealsByCategory(category);
    }
    public Single<CategoryAllMeals> getMealsByCountry(String country){
        return mealservice.getMealsByCountry(country);
    }

    public Single<CategoryAllMeals> getMealsByIngrediant(String ingrediant){
        return mealservice.getMealsByIngrediant(ingrediant);
    }
    public void dispose() {
        compositeDisposable.dispose();
    }
}