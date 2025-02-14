package com.example.testtracker.presenter.search;

import com.example.testtracker.R;
import com.example.testtracker.models.allcategory.AllCategories;
import com.example.testtracker.models.allcountries.AllCounties;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.presenter.intefaces.SearchPresenter;
import com.example.testtracker.view.interfaces.SearchViewinter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private final SearchViewinter view;
    private final PlannerRemoteDataSource remoteDataSource;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchPresenterImpl(SearchViewinter view, PlannerRemoteDataSource remoteDataSource) {
        this.view = view;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void loadIngredients() {
        remoteDataSource.getAllIngrediants()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AllIngrediants>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull AllIngrediants allIngrediants) {
                        view.showIngredients(allIngrediants.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void loadCategories() {
        remoteDataSource.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AllCategories>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull AllCategories allCategories) {
                        view.showCategories(allCategories.getCategories());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void loadCountries() {
        remoteDataSource.getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AllCounties>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull AllCounties allCounties) {
                        view.showCountries(allCounties.getCountries());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void onChipSelected(int chipId) {
        if (chipId == R.id.ingrediant) {
            loadIngredients();
        } else if (chipId == R.id.category) {
            loadCategories();
        } else if (chipId == R.id.country) {
            loadCountries();
        } else {
            view.showError("Invalid selection");
        }
    }

    public void dispose() {
        compositeDisposable.dispose();
    }
}
