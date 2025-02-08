package com.example.testtracker.main_app.dailymeal.model;

import java.util.ArrayList;
import java.util.List;

public class AllMeals {
    List<Meal>meals;
    public List<Meal> getMeals() {
        return meals;
    }
    private AllMeals(){
        meals = new ArrayList<>();
    }
}
