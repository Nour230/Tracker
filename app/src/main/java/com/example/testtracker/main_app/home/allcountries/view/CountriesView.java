package com.example.testtracker.main_app.home.allcountries.view;

import com.example.testtracker.main_app.home.allcountries.model.Country;

import java.util.List;

public interface CountriesView {
    public void showCuntryData(List<Country> countries);
    public void showError(String message);
}
