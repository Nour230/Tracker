package com.example.testtracker.network;

import android.util.Log;

import com.example.testtracker.main_app.allcategories.model.AllCategories;
import com.example.testtracker.main_app.dailymeal.model.AllMeals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repo implements RemoteDataSource {
    private static final String TAG = "productClient";
    private static final String Base_url = "https://www.themealdb.com/api/json/v1/1/";
    private final DailyMealService dailymealservice;
    private final AllCategoriesService categoriesService;
    private static Repo client = null;

    private Repo(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dailymealservice = retrofit.create(DailyMealService.class);
        categoriesService = retrofit.create(AllCategoriesService.class);
    }
    public static Repo getInstance() {
        if (client == null) {
            client = new Repo();
        }
        return client;}
    public void makeNetworkCall(NetworkCallBack networkCallBack){
        Call<AllMeals> mealcall = dailymealservice.getAllMeals();
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

      Call<AllCategories>catCall = categoriesService.getAllCategories();
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
    }


}
