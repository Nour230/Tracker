package com.example.testtracker.network;

public class Meal {
    String idMeal;
    String strMeal;
    String strMealThumb;

    public Meal(String id, String name, String strImageSource) {
        this.idMeal = id;
        this.strMeal = name;
        this.strMealThumb = strImageSource;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }
}
