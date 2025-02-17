package com.example.testtracker.view.home;

import android.app.AlertDialog;
import android.app.TaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testtracker.R;
import com.example.testtracker.models.allcategory.CategoriesRepositoryImpl;
import com.example.testtracker.models.allcategory.Category;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.presenter.home.CategoriesPresenterImpl;
import com.example.testtracker.utils.DateUtile;
import com.example.testtracker.view.adapter.CategoriesAdapter;
import com.example.testtracker.view.forms.Dialog;
import com.example.testtracker.view.interfaces.CategoriesView;
import com.example.testtracker.models.allcountries.Country;
import com.example.testtracker.models.allcountries.CountryRepositoryImpl;
import com.example.testtracker.presenter.home.CountriesPresenterImpl;
import com.example.testtracker.view.interfaces.CountriesView;
import com.example.testtracker.view.adapter.countriesAdapter;
import com.example.testtracker.presenter.home.DailyMealPresenterImpl;
import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.view.adapter.DailyAdaoter;
import com.example.testtracker.view.interfaces.DailyMealView;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.view.interfaces.OnDateSelectedListener;
import com.example.testtracker.view.interfaces.OnMealClickListener;
import com.example.testtracker.view.mealdetails.MealDetailsFragmentArgs;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements OnMealClickListener, CountriesView, OnDateSelectedListener,
         DailyMealView, CategoriesView {

    DailyAdaoter dailyadapter;
    CategoriesAdapter categoryadapter;
    countriesAdapter countryadapter;
    GridView countrygridview;
    SharedPreferences sharedPreferences;
    LinearLayoutManager dailylinearLayoutManager,categorylinearLayoutManager;
    RecyclerView dailyrecyclerView,categoryrecyclerView;
    DailyMealPresenterImpl mealpresenter;
    CategoriesPresenterImpl catpresenter;
    CountriesPresenterImpl countrypresenter;

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
        sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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
                 MealRepositoryImpl.getInstance(getContext()),getContext());
        mealpresenter.getProducts();
        mealpresenter.getData();
        catpresenter = new CategoriesPresenterImpl(this,
                CategoriesRepositoryImpl.getInstance(getContext()));
        catpresenter.getCategories();
        countrypresenter = new CountriesPresenterImpl(this,
                CountryRepositoryImpl.getInstance(getContext()));
        countrypresenter.getCountries();


    }

    @Override
    public void showData(List< MealDetails.MealsDTO> products) {

        dailyadapter.updateData(products);
    }

    @Override
    public void addToFav() {
        Toast.makeText(getContext(), "Added to Fav", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addToPlan() {
        Toast.makeText(getContext(), "Added to Plan", Toast.LENGTH_SHORT).show();
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
    public void onButtonClick(SavedMeals meal) {
        if (mealpresenter != null) {
            mealpresenter.addToFav(meal);
        }
    }


    @Override
    public void onMealClick(String mealId, View view) {
        String userId = sharedPreferences.getString("id", null);
        if (userId == null) {
            Dialog.showAlertDialog(getContext(),getString(R.string.sign_up_for_more_details));
        }else if (mealpresenter != null) {
            mealpresenter.fetchMealDetails(mealId);
        }
    }

    @Override
    public void showMealDetails(MealDetails.MealsDTO mealDetails) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealDetails);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDateSelected(String formattedDate) {
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        MealDetails.MealsDTO mealDetails = args.getMeal();
        String userId = sharedPreferences.getString("id", null);
        SavedMeals planMeal = new SavedMeals(mealDetails.getIdMeal(), userId, formattedDate, mealDetails, false, true);

        if (mealDetails != null) {
            mealpresenter.addToPlan(planMeal); // Pass the meal details and selected date
        }
    }
}