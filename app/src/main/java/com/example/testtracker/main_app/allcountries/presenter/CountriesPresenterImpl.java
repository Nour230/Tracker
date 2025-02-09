package com.example.testtracker.main_app.allcountries.presenter;

import com.example.testtracker.main_app.allcategories.model.Category;
import com.example.testtracker.main_app.allcountries.model.Country;
import com.example.testtracker.main_app.allcountries.model.CountryRepositoryImpl;
import com.example.testtracker.main_app.allcountries.view.CountriesView;
import com.example.testtracker.main_app.dailymeal.model.Meal;
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
    public void onSuccess(List<Meal> products) {
    }

    @Override
    public void onCategoriesSuccess(List<Category> categories) {

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
