package com.example.testtracker.presenter.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.view.interfaces.DailyMealView;
import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.presenter.intefaces.DailyMealPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DailyMealPresenterImpl implements DailyMealPresenter {
    private final DailyMealView view;
    private final MealRepositoryImpl repo;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    public DailyMealPresenterImpl(DailyMealView view, MealRepositoryImpl repo,Context context) {
        this.view = view;
        this.repo = repo;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void getProducts() {
        view.showLoading();
        repo.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List< MealDetails.MealsDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Handle subscription if needed
                    }

                    @Override
                    public void onSuccess(@NonNull List< MealDetails.MealsDTO> meals) {
                        view.hideLoading();
                        view.showData(meals);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showError(e.getMessage());
                    }

                });
    }

    public void fetchMealDetails(String mealId) {

        repo.getMealDetails(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealDetails.MealsDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Handle subscription if needed
                    }

                    @Override
                    public void onSuccess(MealDetails.@NonNull MealsDTO mealsDTO) {

                        view.showMealDetails(mealsDTO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                });
    }

    @Override
    public void addToFav(SavedMeals meal) {
          repo.addMeal(meal)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(
                          () -> {
                              view.addToFav();
                          },
                          error -> {
                              view.showError(error.getMessage());}
                  );

    }

    public void addToPlan(SavedMeals meal){
        repo.addMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToPlan();
                        },
                        error -> {
                            view.showError(error.getMessage());}
                );
    }

    public void getData(){
        repo.getDataFromFiteBase();
    }

}