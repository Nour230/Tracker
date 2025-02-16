package com.example.testtracker.presenter;

import android.util.Log;

import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.presenter.intefaces.FavPresenter;
import com.example.testtracker.view.interfaces.FavView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavPresenterImpl implements FavPresenter {
    private final FavView view;
    private final MealRepositoryImpl repo;

    public FavPresenterImpl(FavView view, MealRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
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
                        view.showData(savedMeals);
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
}