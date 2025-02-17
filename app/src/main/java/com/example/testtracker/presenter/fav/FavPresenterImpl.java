package com.example.testtracker.presenter.fav;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.presenter.intefaces.FavPresenter;
import com.example.testtracker.view.interfaces.FavView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavPresenterImpl implements FavPresenter {
    private final FavView view;
    private final MealRepositoryImpl repo;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    public FavPresenterImpl(FavView view, MealRepositoryImpl repo,Context context) {
        this.view = view;
        this.repo = repo;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void getFavMeals() {
        repo.getAllSavedMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                new SingleObserver<List<SavedMeals>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<SavedMeals> savedMeals) {
                        view.showFavData(savedMeals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("MainActivity", "onError: "+e.getMessage());
                    }
                }
        );
    }

    @Override
    public void deletFromFav(SavedMeals meal) {
        repo.deleteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.deleteFromFav();
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );

    }
    public void deleteData(SavedMeals meal){
        String id = sharedPreferences.getString("id", null);
        myRef.child("Users").child(id).child(meal.getIdMeal()).child(meal.getDate()).removeValue();
    }
}