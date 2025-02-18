package com.example.testtracker.view.fav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtracker.R;
import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.presenter.fav.FavPresenterImpl;
import com.example.testtracker.presenter.home.DailyMealPresenterImpl;
import com.example.testtracker.view.adapter.FavAdapter;
import com.example.testtracker.view.home.HomeFragmentDirections;
import com.example.testtracker.view.interfaces.DailyMealView;
import com.example.testtracker.view.interfaces.FavView;
import com.example.testtracker.view.interfaces.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment implements FavView, OnMealClickListener, DailyMealView {

    private static final String TAG = "MainActivity";
    Group group;
    List<SavedMeals> remainmeals;
    DailyMealPresenterImpl mealpresenter;
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
        mealpresenter = new DailyMealPresenterImpl(this,
                MealRepositoryImpl.getInstance(getContext()), getContext());
        presenter = new FavPresenterImpl(this, MealRepositoryImpl.getInstance(requireContext()), getContext());
        presenter.getFavMeals();
    }


    @Override
    public void showFavData(List<SavedMeals> meals) {
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

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void deleteFromFav(SavedMeals meal) {
        presenter.deletFromFav(meal);
        presenter.deleteData(meal);
    }

    @Override
    public void onMealClick(String mealId, View view) {
        if (mealpresenter != null) {
            mealpresenter.fetchMealDetails(mealId);
        }
    }
    @Override
    public void showMealDetails(MealDetails.MealsDTO mealDetails) {
        FavFragmentDirections.ActionFavFragmentToMealDetailsFragment action =
                FavFragmentDirections.actionFavFragmentToMealDetailsFragment(mealDetails);
        action.setIsFav(true);
        Navigation.findNavController(requireView()).navigate(action);
    }

}