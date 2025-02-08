package com.example.testtracker.network;

import android.util.Log;

import com.example.testtracker.dailymeal.model.AllMeals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repo implements MealRemoteDataSource{
    private static final String TAG = "productClient";
    private static final String Base_url = "https://www.themealdb.com/api/json/v1/1/";
    private final DailyMealService service;
    private static Repo client = null;

    private Repo(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DailyMealService.class);
    }
    public static Repo getInstance() {
        if (client == null) {
            client = new Repo();
        }
        return client;}
    public void makeNetworkCall(NetworkCallBack networkCallBack){
        Call<AllMeals> call =service.getAllMeals();
      call.enqueue(new Callback<AllMeals>(){

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
    }
}
