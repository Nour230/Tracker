package com.example.testtracker.main_app.mealdetails.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtracker.R;
import com.example.testtracker.main_app.mealdetails.model.MealDetails;
import com.example.testtracker.main_app.mealdetails.model.MealDetailsRepositoryImpl;
import com.example.testtracker.main_app.mealdetails.presenter.MealDetailsPresenterImpl;
import com.example.testtracker.network.Repo;

import java.util.ArrayList;

public class MealDetailsFragment extends Fragment implements MealDetailsView{
    private static final String TAG = "MainActivity";
    MealDetailsPresenterImpl presenter;
    MealDetailsAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TextView name, country, category, instructions;

    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.mealName);
        country = view.findViewById(R.id.country);
        category = view.findViewById(R.id.caregory);
        instructions = view.findViewById(R.id.instructions);
        recyclerView = view.findViewById(R.id.ingrediantrec);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MealDetailsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        presenter = new MealDetailsPresenterImpl(this, MealDetailsRepositoryImpl.getInstance(Repo.getInstance()));
    }

    @Override
    public void showMealDetails(MealDetails.MealsDTO meal) {
        
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}