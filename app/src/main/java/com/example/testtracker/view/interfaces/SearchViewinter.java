package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.allcategory.Category;
import com.example.testtracker.models.allcountries.Country;
import com.example.testtracker.models.mealdetails.AllIngrediants;

import java.util.List;

public interface SearchViewinter {
    void showIngredients(List<AllIngrediants.Ingrediants> ingredients);
    void showCategories(List<Category> categories);
    void showCountries(List<Country> countries);
    void showError(String message);
}
