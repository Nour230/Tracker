package com.example.testtracker.network;


import com.example.testtracker.main_app.allcategories.model.AllCategories;
import com.example.testtracker.main_app.dailymeal.model.AllMeals;

import retrofit2.Call;
import retrofit2.http.GET;
 interface DailyMealService {
    @GET("random.php")
    Call<AllMeals> getAllMeals();
}

interface AllCategoriesService {
    @GET("categories.php")
    Call<AllCategories> getAllCategories();
}


