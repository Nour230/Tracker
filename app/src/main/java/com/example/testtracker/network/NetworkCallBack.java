package com.example.testtracker.network;

import com.example.testtracker.models.catandcountrymeals.CategoryMeals;
import com.example.testtracker.models.allcategory.Category;
import com.example.testtracker.models.allcountries.Country;
import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.models.mealdetails.MealDetails;

import java.util.List;

public interface NetworkCallBack {
    default void onSuccess(List<Meal> products) {
    }

    default void onIngrediantSuccess(List<AllIngrediants.Ingrediants> ingredients) {
    }

    default void onCategoriesSuccess(List<Category> categories) {
    }

    default void onCountrySuccess(List<Country> countries) {
    }

    default void onCategoryMealsSuccess(List<CategoryMeals> meal) {
    }

    default void onCountryMealsSuccess(List<CategoryMeals> meal) {
    }

    default void onMealSussecc(List<MealDetails.MealsDTO> meals){}

    void onFailure(String message);
}
