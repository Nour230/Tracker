package com.example.testtracker.view.fav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtracker.R;
import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.presenter.FavPresenterImpl;
import com.example.testtracker.view.adapter.FavAdapter;
import com.example.testtracker.view.interfaces.FavView;
import com.example.testtracker.view.interfaces.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment implements FavView, OnMealClickListener {

    private static final String TAG = "MainActivity";
    Group group;
    List<SavedMeals> remainmeals;
    private FavPresenterImpl presenter;
    private RecyclerView recyclerView;
    private FavAdapter adapter;

    public FavFragment() {
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
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favrec);
        group = view.findViewById(R.id.favgroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        presenter = new FavPresenterImpl(this, MealRepositoryImpl.getInstance(requireContext()));
        presenter.getFavMeals();
    }

    @Override
    public void showData(List<SavedMeals> meals) {
        remainmeals = meals;
        if (meals == null || meals.isEmpty()) {
            group.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            group.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.updateData(meals);
        }

    }

    @Override
    public void deleteFromFav() {
        int size = remainmeals.size();
        if (size == 0) {
            group.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            group.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        size = remainmeals.size() - 1;

        Toast.makeText(getContext(), "Meal Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void deleteFromFav(SavedMeals meal) {
        presenter.deletFromFav(meal);
    }
}