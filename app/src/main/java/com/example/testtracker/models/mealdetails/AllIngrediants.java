package com.example.testtracker.models.mealdetails;

import java.util.List;

public class AllIngrediants {

    private List<Ingrediants> meals;

    public List<Ingrediants> getMeals() {
        return meals;
    }

    public void setMeals(List<Ingrediants> meals) {
        this.meals = meals;
    }

    public static class Ingrediants {
        private String idIngredient;
        private String strIngredient;

        public String getIdIngredient() {
            return idIngredient;
        }

        public void setIdIngredient(String idIngredient) {
            this.idIngredient = idIngredient;
        }

        public String getStrIngredient() {
            return strIngredient;
        }

        public void setStrIngredient(String strIngredient) {
            this.strIngredient = strIngredient;
        }
    }
}
