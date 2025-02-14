package com.example.testtracker.presenter.home;

import com.example.testtracker.models.allcategory.CategoriesRepositoryImpl;
import com.example.testtracker.models.allcategory.Category;
import com.example.testtracker.presenter.intefaces.CategoriesPresenter;
import com.example.testtracker.view.interfaces.CategoriesView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesPresenterImpl implements CategoriesPresenter {
    private final CategoriesView view;
    private final CategoriesRepositoryImpl repo;

    public CategoriesPresenterImpl(CategoriesView view, CategoriesRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }
    @Override
    public void getCategories() {
        repo.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Category>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Category> categories) {
                        view.showCatData(categories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });

    }
}
