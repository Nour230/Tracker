package com.example.testtracker.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.view.interfaces.OnMealClickListener;
import com.google.android.material.button.MaterialButton;

import java.util.Date;
import java.util.List;

public class DailyAdaoter extends RecyclerView.Adapter<DailyAdaoter.MealViewHolder> {
    private final static String TAG = "MainActivity";
    private final Context context;
    private final List<MealDetails.MealsDTO> dailyList;
    private final OnMealClickListener listener;
    SharedPreferences sharedPreferences;

    public DailyAdaoter(List<MealDetails.MealsDTO> dailyList, Context context, OnMealClickListener listener) {
        this.listener = listener;
        this.dailyList = dailyList;
        this.context = context;
    }


    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealDetails.MealsDTO meal = dailyList.get(position);
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", null);
        SavedMeals favMeal = new SavedMeals(meal.getIdMeal(), userId, "fav", meal, true, false);

        holder.name.setText("Name: " + meal.getStrMeal());
        holder.area.setText("Area: " + meal.getStrArea());
        holder.category.setText("Category: " + meal.getStrCategory());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.image);
        holder.cardView.setOnClickListener(v -> {
            if (meal != null && listener != null) {
                listener.onMealClick(meal.getIdMeal(), v);
            }
        });
        holder.fav.setOnClickListener(v -> {
            if (userId != null && listener != null) {
                listener.onButtonClick(favMeal);
                holder.fav.setText("Added to Fav");
            }
        });

    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public void updateData(List<MealDetails.MealsDTO> newProducts) {
        this.dailyList.clear();
        this.dailyList.addAll(newProducts);
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView name, area, category;
        ImageView image, plan;
        CardView cardView;
        MaterialButton fav;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mealName);
            image = itemView.findViewById(R.id.mealImage);
            area = itemView.findViewById(R.id.mealArea);
            category = itemView.findViewById(R.id.mealCat);
            cardView = itemView.findViewById(R.id.daymeal);
            fav = itemView.findViewById(R.id.addtofav);
        }
    }
}
