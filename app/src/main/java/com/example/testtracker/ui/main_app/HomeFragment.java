package com.example.testtracker.ui.main_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.testtracker.R;
import com.example.testtracker.adapters.DailyAdaoter;
import com.example.testtracker.network.Meal;
import com.example.testtracker.network.NetworkCallBack;
import com.example.testtracker.network.Repo;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements NetworkCallBack {

    DailyAdaoter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";

    private List<Meal> dailyList = new ArrayList<>();
    Repo MealClient;
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
        adapter = new DailyAdaoter(dailyList, getContext());
        recyclerView.setAdapter(adapter);
        MealClient = Repo.getInstance();
        MealClient.makeNetworkCall(this);
    }

    @Override
    public void onSuccess(List<Meal> products) {
        Log.i(TAG, "onSuccess:"+products.get(0).getStrMeal());
        this.dailyList.clear();
        this.dailyList.addAll(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}