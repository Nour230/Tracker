package com.example.testtracker.main_app.categorymeals.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testtracker.R;
import com.example.testtracker.main_app.categorymeals.model.CategoryMeals;
import com.example.testtracker.main_app.categorymeals.model.CategoryMealsReposetoryImpl;
import com.example.testtracker.main_app.categorymeals.presenter.CategoryMealsPresenterImpl;
import com.example.testtracker.network.Repo;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMealsFragment extends Fragment implements CategoryMealsView, OnMealClickListener {
    CategoryMealsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    CategoryMealsPresenterImpl presenter;
    TextView name;

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
                CategoryMealsReposetoryImpl.getInstance(Repo.getInstance()));
        String categoryName = CategoriesMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        presenter.getMealsByCategory(categoryName); // Pass to presenter

    }

    @Override
    public void onMealClick(CategoryMeals meal) {

    }

    @Override
    public void showCatData(List<CategoryMeals> products) {
        adapter.updateData(products);
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}