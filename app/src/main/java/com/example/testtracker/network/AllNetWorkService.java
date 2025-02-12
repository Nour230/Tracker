package com.example.testtracker.network;


import com.example.testtracker.models.catandcountrymeals.CategoryAllMeals;
import com.example.testtracker.models.allcategory.AllCategories;
import com.example.testtracker.models.allcountries.AllCounties;
import com.example.testtracker.models.dailymeal.AllMeals;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.models.mealdetails.MealDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AllNetWorkService {
    @GET("random.php")
    Call<AllMeals> getAllMeals();
     @GET("categories.php")
     Call<AllCategories> getAllCategories();

     @GET("list.php?a=list")
     Call<AllCounties> getAllCountries();

     @GET("filter.php")
     Call<CategoryAllMeals> getMealsByCategory(@Query("c") String category);

     @GET("filter.php")
    Call<CategoryAllMeals> getMealsByCountry(@Query("a") String country);

     @GET("list.php?i=list")
     Call<AllIngrediants> getAllIngredients();
     @GET("lookup.php")
     Call<MealDetails> getMealDetails(@Query("i") String id);
}




