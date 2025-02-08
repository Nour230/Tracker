package com.example.testtracker.dailymeal.view;

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

import com.example.testtracker.R;
import com.example.testtracker.dailymeal.presenter.DailyMealPresenterImpl;
import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.dailymeal.model.Meal;
import com.example.testtracker.dailymeal.model.MealRepositoryImpl;
import com.example.testtracker.network.Repo;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements OnMealClickListener,DailyMealView {

    DailyAdaoter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    DailyMealPresenterImpl presenter;
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
        recyclerView = view.findViewById(R.id.dayrec);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DailyAdaoter(new ArrayList<>(), getContext(),this);
        recyclerView.setAdapter(adapter);
        presenter = new DailyMealPresenterImpl(this,
                 MealRepositoryImpl.getInstance(
                        MealLocalDataSourceImpl.getInstance(getContext()),
                        Repo.getInstance()));
        presenter.getProducts();
    }

    @Override
    public void showData(List<Meal> products) {
        adapter.updateData(products);
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onMealClick(Meal meal) {
        presenter.addToFav(meal);
    }
}