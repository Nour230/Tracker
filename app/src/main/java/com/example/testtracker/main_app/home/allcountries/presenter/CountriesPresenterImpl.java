package com.example.testtracker.main_app.home.allcountries.presenter;

import com.example.testtracker.main_app.home.allcountries.model.Country;
import com.example.testtracker.main_app.home.allcountries.model.CountryRepositoryImpl;
import com.example.testtracker.main_app.home.allcountries.view.CountriesView;
import com.example.testtracker.network.NetworkCallBack;

import java.util.List;

public class CountriesPresenterImpl implements NetworkCallBack, CountriesPresenter{

    private final CountriesView view;
    private final CountryRepositoryImpl repo;

    public CountriesPresenterImpl(CountriesView view, CountryRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getCountries() {
        repo.getAllCountries(this);
    }
    @Override
    public void onCountrySuccess(List<Country> countries) {
        view.showCuntryData(countries);

    }

    @Override
    public void onFailure(String message) {
        view.showError(message);
    }
}
