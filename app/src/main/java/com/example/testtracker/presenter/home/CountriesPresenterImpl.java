package com.example.testtracker.presenter.home;

import com.example.testtracker.models.allcountries.Country;
import com.example.testtracker.models.allcountries.CountryRepositoryImpl;
import com.example.testtracker.presenter.intefaces.CountriesPresenter;
import com.example.testtracker.view.interfaces.CountriesView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountriesPresenterImpl implements CountriesPresenter {

    private final CountriesView view;
    private final CountryRepositoryImpl repo;

    public CountriesPresenterImpl(CountriesView view, CountryRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getCountries() {
        repo.getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Country>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Country> countries) {
                        view.showCuntryData(countries);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

}
