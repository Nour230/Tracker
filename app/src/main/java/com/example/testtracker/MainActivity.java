package com.example.testtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testtracker.main_app.categorymeals.model.CategoryMeals;
import com.example.testtracker.main_app.mealdetails.model.AllIngrediants;
import com.example.testtracker.main_app.mealdetails.model.MealDetails;
import com.example.testtracker.network.NetworkCallBack;
import com.example.testtracker.network.Repo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkCallBack {
    private static final String TAG = "MainActivity";
    private Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_main);
        repo = Repo.getInstance();
        repo.makeNetworkCall(this);
        //repo.getMealsByCategory("Canadian",this);
        repo.getMealsByCountry("Canadian", this);
        repo.getMealDetails("52959", this);


    }


    @Override
    public void onIngrediantSuccess(List<AllIngrediants.Ingrediants> ingredients) {
        Log.i(TAG, "onIngrediantSuccess: " + ingredients.get(0).getStrIngredient());
    }

    @Override
    public void onCountryMealsSuccess(List<CategoryMeals> categories) {
        if (categories != null) {
            Log.i(TAG, "onCategoryMealsSuccess: CallBack" + categories.get(0).getStrMeal());
        } else {
            Log.e(TAG, "onSuccess: onCategoryMealsSuccess list is null");
        }
    }

    @Override
    public void onMealSussecc(List<MealDetails.MealsDTO> meals) {
        Log.i(TAG, "onMealSussecc: "+meals.get(0).getStrMeal());
    }

    @Override
    public void onFailure(String message) {
        Log.i(TAG, "onFailure: CallBack" + message);

    }
}