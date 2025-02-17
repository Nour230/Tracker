package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.db.SavedMeals;

import java.util.List;

public interface FavView {
    public void showFavData(List<SavedMeals> meals);
    public void deleteFromFav();
    public void showError(String message);
}
