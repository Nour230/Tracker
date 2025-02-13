package com.example.testtracker.models.dailymeal;

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
