package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.allcountries.Country;

import java.util.List;

public interface CountriesView {
    public void showCuntryData(List<Country> countries);
    public void showError(String message);
}
