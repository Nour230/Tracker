package com.example.testtracker.main_app.home.view;

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
import android.widget.GridView;

import com.example.testtracker.R;
import com.example.testtracker.db.MealDAO;
import com.example.testtracker.main_app.home.allcategories.model.CategoriesRepositoryImpl;
import com.example.testtracker.main_app.home.allcategories.model.Category;
import com.example.testtracker.main_app.home.allcategories.presenter.CategoriesPresenterImpl;
import com.example.testtracker.main_app.home.allcategories.view.CategoriesAdapter;
import com.example.testtracker.main_app.home.allcategories.view.CategoriesView;
import com.example.testtracker.main_app.home.allcategories.view.OnCaregoryClickListener;
import com.example.testtracker.main_app.home.allcountries.model.Country;
import com.example.testtracker.main_app.home.allcountries.model.CountryRepositoryImpl;
import com.example.testtracker.main_app.home.allcountries.presenter.CountriesPresenterImpl;
import com.example.testtracker.main_app.home.allcountries.view.CountriesView;
import com.example.testtracker.main_app.home.allcountries.view.OnContryClickListener;
import com.example.testtracker.main_app.home.allcountries.view.countriesAdapter;
import com.example.testtracker.main_app.home.dailymeal.presenter.DailyMealPresenterImpl;
import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.main_app.home.dailymeal.model.Meal;
import com.example.testtracker.main_app.home.dailymeal.model.MealRepositoryImpl;
import com.example.testtracker.main_app.home.dailymeal.view.DailyAdaoter;
import com.example.testtracker.main_app.home.dailymeal.view.DailyMealView;
import com.example.testtracker.main_app.home.dailymeal.view.OnMealClickListener;
import com.example.testtracker.main_app.mealdetails.model.MealDetails;
import com.example.testtracker.network.Repo;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements OnMealClickListener, CountriesView,
        OnContryClickListener, DailyMealView, OnCaregoryClickListener, CategoriesView {

    DailyAdaoter dailyadapter;
    CategoriesAdapter categoryadapter;
    countriesAdapter countryadapter;
    GridView countrygridview;
    LinearLayoutManager dailylinearLayoutManager,categorylinearLayoutManager;
    RecyclerView dailyrecyclerView,categoryrecyclerView;
    DailyMealPresenterImpl mealpresenter;
    CategoriesPresenterImpl catpresenter;
    CountriesPresenterImpl countrypresenter;
    private static final String TAG = "MainActivity";

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //recyclerView
        dailyrecyclerView = view.findViewById(R.id.dayrec);
        categoryrecyclerView = view.findViewById(R.id.catrec);
        countrygridview = view.findViewById(R.id.countryrec);

        //linearLayoutManager
        dailylinearLayoutManager = new LinearLayoutManager(getContext());
        dailylinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categorylinearLayoutManager = new LinearLayoutManager(getContext());
        categorylinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //recyclerView
        dailyrecyclerView.setLayoutManager(dailylinearLayoutManager);
        categoryrecyclerView.setLayoutManager(categorylinearLayoutManager);

        //adapter
        countryadapter = new countriesAdapter(new ArrayList<>(), getContext(),this);
        dailyadapter = new DailyAdaoter(new ArrayList<>(), getContext(),this);
        categoryadapter = new CategoriesAdapter(new ArrayList<>(), getContext(),this);
        dailyrecyclerView.setAdapter(dailyadapter);
        categoryrecyclerView.setAdapter(categoryadapter);
        countrygridview.setAdapter(countryadapter);


        //presenter
        mealpresenter = new DailyMealPresenterImpl(this,
                 MealRepositoryImpl.getInstance(
                        MealLocalDataSourceImpl.getInstance(getContext()),
                        Repo.getInstance()));
        mealpresenter.getProducts();
        catpresenter = new CategoriesPresenterImpl(this,
                CategoriesRepositoryImpl.getInstance(
                        Repo.getInstance()));
        catpresenter.getCategories();
        countrypresenter = new CountriesPresenterImpl(this,
                CountryRepositoryImpl.getInstance(
                        Repo.getInstance()));
        countrypresenter.getCountries();

    }

    @Override
    public void showData(List<Meal> products) {
        dailyadapter.updateData(products);
    }


    @Override
    public void showCatData(List<Category> products) {
     categoryadapter.updateData(products);
    }

    @Override
    public void showCuntryData(List<Country> countries) {
        countryadapter.updateData(countries);
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
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealDetails);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onCategoryClick(String category, View view) {
        HomeFragmentDirections.ActionHomeFragmentToCategoriesMealsFragment action =
                HomeFragmentDirections.actionHomeFragmentToCategoriesMealsFragment(category);
        action.setIsCountry(false); // Set flag to indicate it's a category
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onContryClick(String country, View view) {
        HomeFragmentDirections.ActionHomeFragmentToCategoriesMealsFragment action =
                HomeFragmentDirections.actionHomeFragmentToCategoriesMealsFragment(country);
        action.setIsCountry(true); // Set flag to indicate it's a country
        Navigation.findNavController(view).navigate(action);
    }


    @Override
    public void onMealClick(String mealId, View view) {
        Log.i(TAG, "onMealClick: "+mealId);
        if (mealpresenter != null) {
            mealpresenter.fetchMealDetails(mealId);
        }
    }
}