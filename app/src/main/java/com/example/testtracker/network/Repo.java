package com.example.testtracker.network;

import android.util.Log;

import com.example.testtracker.main_app.categorymeals.model.CategoryAllMeals;
import com.example.testtracker.main_app.home.allcategories.model.AllCategories;
import com.example.testtracker.main_app.home.allcountries.model.AllCounties;
import com.example.testtracker.main_app.home.dailymeal.model.AllMeals;
import com.example.testtracker.main_app.mealdetails.model.AllIngrediants;
import com.example.testtracker.main_app.mealdetails.model.MealDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repo implements RemoteDataSource {
    private static final String TAG = "productClient";
    private static final String Base_url = "https://www.themealdb.com/api/json/v1/1/";
    private final AllNetWorkService mealservice;

    private static Repo client = null;

    private Repo(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealservice = retrofit.create(AllNetWorkService.class);
}
    public static Repo getInstance() {
        if (client == null) {
            client = new Repo();
        }
        return client;}
    public void makeNetworkCall(NetworkCallBack networkCallBack){
        //Daily Meal CallBack
        Call<AllMeals> mealcall = mealservice.getAllMeals();
      mealcall.enqueue(new Callback<AllMeals>(){
          @Override
          public void onResponse(Call<AllMeals> call, Response<AllMeals> response) {
              if(response.isSuccessful()){
                  Log.i(TAG, "onResponse: CallBack"+response.raw()+response.body());
                  networkCallBack.onSuccess(response.body().getMeals());
              }
          }
          @Override
          public void onFailure(Call<AllMeals> call, Throwable t) {
              Log.i(TAG, "onFailure: CallBack"+t.getMessage());
              networkCallBack.onFailure(t.getMessage());
              t.printStackTrace();
          }
      });

      //Categories CallBack
      Call<AllCategories>catCall = mealservice.getAllCategories();
      catCall.enqueue(new Callback<AllCategories>() {
          @Override
          public void onResponse(Call<AllCategories> call, Response<AllCategories> response) {
              Log.i(TAG, "onResponse: CallBack "+response.raw()+response.body());
              networkCallBack.onCategoriesSuccess(response.body().getCategories());
          }

          @Override
          public void onFailure(Call<AllCategories> call, Throwable t) {
              Log.i(TAG, "onFailure: CallBack"+t.getMessage());
              networkCallBack.onFailure(t.getMessage());
              t.printStackTrace();
          }
      });

      //Countries CallBack
      Call<AllCounties>countryCall = mealservice.getAllCountries();
      countryCall.enqueue(new Callback<AllCounties>() {

          @Override
          public void onResponse(Call<AllCounties> call, Response<AllCounties> response) {
              Log.i(TAG, "onResponse: AllCounties "+response.raw()+response.body());
              networkCallBack.onCountrySuccess(response.body().getCountries());
          }

          @Override
          public void onFailure(Call<AllCounties> call, Throwable t) {
              Log.i(TAG, "onFailure: CallBack"+t.getMessage());
              networkCallBack.onFailure(t.getMessage());
              t.printStackTrace();
          }
      });
      //ingrediants CallBack
        Call<AllIngrediants>ingCall = mealservice.getAllIngredients();
        ingCall.enqueue(new Callback<AllIngrediants>() {
            @Override
            public void onResponse(Call<AllIngrediants> call, Response<AllIngrediants> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onIngrediantSuccess(response.body().getMeals());
                } else {
                    networkCallBack.onFailure("Failed to fetch meal details.");
                }
            }

            @Override
            public void onFailure(Call<AllIngrediants> call, Throwable t) {
                Log.i(TAG, "onFailure: CallBack"+t.getMessage());
                networkCallBack.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }
    public void getMealsByCategory(String category, NetworkCallBack networkCallBack){
        Call<CategoryAllMeals> mealcall = mealservice.getMealsByCategory(category);
        mealcall.enqueue(new Callback<CategoryAllMeals>() {
            @Override
            public void onResponse(Call<CategoryAllMeals> call, Response<CategoryAllMeals> response) {
                Log.i(TAG, "onResponse: CategoryAllMeals"+response.raw()+response.body());
                networkCallBack.onCategoryMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<CategoryAllMeals> call, Throwable t) {
                Log.i(TAG, "onFailure: CallBack"+t.getMessage());
                networkCallBack.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void getMealsByCountry(String country, NetworkCallBack networkCallBack){
        Call<CategoryAllMeals> mealcall = mealservice.getMealsByCountry(country);
        mealcall.enqueue(new Callback<CategoryAllMeals>() {
            @Override
            public void onResponse(Call<CategoryAllMeals> call, Response<CategoryAllMeals> response) {
                Log.i(TAG, "onResponse: getMealsByCountry"+response.raw()+response.body());
                networkCallBack.onCountryMealsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<CategoryAllMeals> call, Throwable t) {
                Log.i(TAG, "onFailure: CallBack"+t.getMessage());
                networkCallBack.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getMealDetails(String id, NetworkCallBack networkCallBack) {
        Call<MealDetails>meal = mealservice.getMealDetails(id);
        meal.enqueue(new Callback<MealDetails>() {

            @Override
            public void onResponse(Call<MealDetails> call, Response<MealDetails> response) {
                Log.i(TAG, "onResponse: "+response.body().getMeals().get(0).getStrMeal());
                networkCallBack.onMealSussecc(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealDetails> call, Throwable t) {
                Log.i(TAG, "onFailure: CallBack"+t.getMessage());
                networkCallBack.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }


}
