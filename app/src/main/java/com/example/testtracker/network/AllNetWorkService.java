package com.example.testtracker.network;


import com.example.testtracker.models.catandcountrymeals.CategoryAllMeals;
import com.example.testtracker.models.allcategory.AllCategories;
import com.example.testtracker.models.allcountries.AllCounties;
import com.example.testtracker.models.dailymeal.AllMeals;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.models.mealdetails.MealDetails;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AllNetWorkService {
    @GET("random.php")
    Single<AllMeals> getAllMeals();
     @GET("categories.php")
     Single<AllCategories> getAllCategories();

     @GET("list.php?a=list")
     Single<AllCounties> getAllCountries();
    @GET("list.php?i=list")
    Single<AllIngrediants> getAllIngredients();
     @GET("filter.php")
     Single<CategoryAllMeals> getMealsByCategory(@Query("c") String category);

     @GET("filter.php")
    Single<CategoryAllMeals> getMealsByCountry(@Query("a") String country);


     @GET("lookup.php")
     Single<MealDetails> getMealDetails(@Query("i") String id);

     @GET("filter.php")
     Single<CategoryAllMeals> getMealsByIngrediant(@Query("i") String ingrediant);
}




