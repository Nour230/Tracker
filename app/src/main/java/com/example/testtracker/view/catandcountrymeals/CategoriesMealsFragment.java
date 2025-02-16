package com.example.testtracker.view.catandcountrymeals;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testtracker.R;
import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.models.catandcountrymeals.CategoryMeals;
import com.example.testtracker.models.catandcountrymeals.CategoryMealsReposetoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.presenter.catandcountrymeals.CategoryMealsPresenterImpl;
import com.example.testtracker.view.interfaces.CategoryMealsView;
import com.example.testtracker.view.interfaces.OnMealClickListener;
import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.presenter.home.DailyMealPresenterImpl;
import com.example.testtracker.view.interfaces.DailyMealView;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.view.adapter.CategoryMealsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMealsFragment extends Fragment implements CategoryMealsView, OnMealClickListener, DailyMealView {
    CategoryMealsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    CategoryMealsPresenterImpl presenter;
    TextView name;
    DailyMealPresenterImpl mealpresenter;
    private static final String TAG = "MainActivity";


    public CategoriesMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.catMealName);
        recyclerView = view.findViewById(R.id.catmealsrec);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CategoryMealsAdapter(new ArrayList<>(),getContext(),this);
        recyclerView.setAdapter(adapter);
        presenter = new CategoryMealsPresenterImpl(this,
                CategoryMealsReposetoryImpl.getInstance(getContext()));
        String categoryName = CategoriesMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        Log.i(TAG, "onViewCreated: "+categoryName);
        String categoryOrCountryName = CategoriesMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        boolean isCountry = CategoriesMealsFragmentArgs.fromBundle(getArguments()).getIsCountry();
        boolean isIngrediant = CategoriesMealsFragmentArgs.fromBundle(getArguments()).getIsIngrediant();

        Log.i(TAG, "onViewCreated: Name=" + categoryOrCountryName + " | isCountry=" + isCountry);

        // Use the flag to determine the appropriate API call
        if (isCountry) {
            presenter.getMealsByCountry(categoryOrCountryName);
        } else if(isIngrediant){
           presenter.getMealsByIngrediant(categoryOrCountryName);
        }else {
            presenter.getMealsByCategory(categoryOrCountryName);
        }
        mealpresenter = new DailyMealPresenterImpl(this,
                MealRepositoryImpl.getInstance(getContext()));
        mealpresenter.getProducts();

    }



    @Override
    public void showCatData(List<MealDetails.MealsDTO> products) {
        adapter.updateData(products);
    }

    @Override
    public void addToFav() {

    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showMealDetails(MealDetails.MealsDTO mealDetails) {
        Log.i(TAG, "showMealDetails: "+mealDetails.getStrIngredient4());
        CategoriesMealsFragmentDirections.ActionCategoriesMealsFragmentToMealDetailsFragment action =
                CategoriesMealsFragmentDirections.actionCategoriesMealsFragmentToMealDetailsFragment(mealDetails);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onMealClick(String mealId, View view) {
        Log.i(TAG, "onMealClick: "+mealId);
        if (mealpresenter != null) {
            mealpresenter.fetchMealDetails(mealId);
        }
    }

    @Override
    public void onButtonClick(SavedMeals meal) {

    }
}