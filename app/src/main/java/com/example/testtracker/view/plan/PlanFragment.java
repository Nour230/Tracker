package com.example.testtracker.view.plan;

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
import android.widget.Switch;

import com.example.testtracker.R;
import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.presenter.plan.PlanPresenterImpl;
import com.example.testtracker.view.adapter.FavAdapter;
import com.example.testtracker.view.adapter.PlanAdapter;
import com.example.testtracker.view.fav.FavFragmentDirections;
import com.example.testtracker.view.interfaces.DailyMealView;
import com.example.testtracker.view.interfaces.OnMealClickListener;
import com.example.testtracker.view.interfaces.PlanView;

import java.util.ArrayList;
import java.util.List;


public class PlanFragment extends Fragment implements PlanView , DailyMealView, OnMealClickListener {
    RecyclerView satrec, sunrec, monrec, tuerec, wedrec, thurec, frirec;
    private PlanAdapter adapter;
    private PlanPresenterImpl presenter;

    List<MealDetails.MealsDTO> satmeals= new ArrayList<>(),
            sunmeals= new ArrayList<>(),
            monmeals= new ArrayList<>(),
            tuemeals= new ArrayList<>(),
            wedmeals= new ArrayList<>(),
            thumeals= new ArrayList<>(),
            frimeals= new ArrayList<>();
    public PlanFragment() {
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
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        satrec = view.findViewById(R.id.satrec);
        sunrec = view.findViewById(R.id.sunrec);
        monrec = view.findViewById(R.id.monrec);
        tuerec = view.findViewById(R.id.tuerec);
        wedrec = view.findViewById(R.id.wedrec);
        thurec = view.findViewById(R.id.thurrec);
        frirec = view.findViewById(R.id.frirec);


        presenter = new PlanPresenterImpl(this, MealRepositoryImpl.getInstance(getContext()));
        presenter.getPlanMeals();

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showPlanMeals(List<SavedMeals> groupedMeals) {
        Log.i("MainActivity", "showPlanMeals: "+groupedMeals.size());
        for(SavedMeals savedMeals : groupedMeals){
            String day = savedMeals.getDate();
            switch (day){
                case "Saturday":
                    satmeals.add(savedMeals.getMeal());
                    adapter = new PlanAdapter(getContext(), satmeals, this);
                    satrec.setAdapter(adapter);
                    adapter.updateData(satmeals);
                    break;
                case "Sunday":
                    sunmeals.add(savedMeals.getMeal());
                    adapter = new PlanAdapter(getContext(), sunmeals, this);
                    sunrec.setAdapter(adapter);
                    adapter.updateData(sunmeals);
                    break;
                case "Monday":
                    monmeals.add(savedMeals.getMeal());
                    adapter = new PlanAdapter(getContext(), monmeals, this);
                    monrec.setAdapter(adapter);
                    adapter.updateData(monmeals);
                    break;
                case "Tuesday":
                    tuemeals.add(savedMeals.getMeal());
                    adapter = new PlanAdapter(getContext(), tuemeals, this);
                    tuerec.setAdapter(adapter);
                    adapter.updateData(tuemeals);
                    break;
                case "Wednesday":
                    wedmeals.add(savedMeals.getMeal());
                    adapter = new PlanAdapter(getContext(), wedmeals, this);
                    wedrec.setAdapter(adapter);
                    adapter.updateData(wedmeals);
                    break;
                case "Thursday":
                    thumeals.add(savedMeals.getMeal());
                    adapter = new PlanAdapter(getContext(), thumeals, this);
                    thurec.setAdapter(adapter);
                    adapter.updateData(thumeals);
                    break;
                case "Friday":
                    frimeals.add(savedMeals.getMeal());
                    adapter = new PlanAdapter(getContext(), frimeals, this);
                    frirec.setAdapter(adapter);
                    adapter.updateData(frimeals);
                    break;
            }
        }
    }
    @Override
    public void onMealClick(String mealId, View view) {
        if (presenter != null) {
            presenter.fetchMealDetails(mealId);
        }
    }
    @Override
    public void showMealDetails(MealDetails.MealsDTO mealDetails) {
        PlanFragmentDirections.ActionPlanFragmentToMealDetailsFragment action =
                PlanFragmentDirections.actionPlanFragmentToMealDetailsFragment(mealDetails);
        action.setIsPlan(true);
        Navigation.findNavController(requireView()).navigate(action);
    }

}