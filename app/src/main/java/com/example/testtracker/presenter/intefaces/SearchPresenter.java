package com.example.testtracker.presenter.intefaces;

public interface SearchPresenter {
    void loadIngredients();
    void loadCategories();
    void loadCountries();
    void onChipSelected(int chipId);

    public void dispose();
}
