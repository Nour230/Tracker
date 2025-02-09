package com.example.testtracker;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testtracker.main_app.allcategories.model.Category;
import com.example.testtracker.main_app.allcountries.model.Country;
import com.example.testtracker.main_app.dailymeal.model.Meal;
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
        setContentView(R.layout.activity_main);
        repo = Repo.getInstance();
        repo.makeNetworkCall(this);
    }


    @Override
    public void onSuccess(List<Meal> products) {
        if (products != null) {
            Log.i(TAG, "onSuccess: CallBack" + products.toString());
        } else {
            Log.e(TAG, "onSuccess: products list is null");
        }    }

    @Override
    public void onCategoriesSuccess(List<Category> categories) {
        if (categories != null) {
            Log.i(TAG, "onCategoriesSuccess: CallBack" + categories.get(0).getStrCategory());
        } else {
            Log.e(TAG, "onSuccess: products list is null");
        }
    }

    @Override
    public void onCountrySuccess(List<Country> countries) {
        if (countries != null) {
            Log.i(TAG, "onCountrySuccess: CallBack" + countries.toString());
        } else {
            Log.e(TAG, "onSuccess: products list is null");
        }
    }

    @Override
    public void onFailure(String message) {
        Log.i(TAG, "onFailure: CallBack"+message);

    }
}