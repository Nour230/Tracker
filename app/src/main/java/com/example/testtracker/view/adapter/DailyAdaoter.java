package com.example.testtracker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.view.interfaces.OnMealClickListener;

import java.util.List;

public class DailyAdaoter extends RecyclerView.Adapter<DailyAdaoter.MealViewHolder> {
    private final static String TAG = "productClient";
    private final Context context;
    private final List<Meal> dailyList;
private final OnMealClickListener listener;
    public DailyAdaoter(List<Meal> dailyList, Context context, OnMealClickListener listener) {
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
        Meal meal = dailyList.get(position);
        holder.name.setText("Name: "+meal.getStrMeal());
        holder.area.setText("Area: "+meal.getStrArea());
        holder.category.setText("Category: "+meal.getStrCategory());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.image);
        holder.cardView.setOnClickListener(v -> {
            if (meal != null && listener != null) {
                listener.onMealClick(meal.getIdMeal(), v); // Ensure getIdMeal() returns the correct ID
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }
    public void updateData(List<Meal> newProducts) {
        this.dailyList.clear();
        this.dailyList.addAll(newProducts);
        notifyDataSetChanged();
    }
    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView name,area,category;
        ImageView image;
        CardView cardView;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mealName);
            image = itemView.findViewById(R.id.mealImage);
            area = itemView.findViewById(R.id.mealArea);
            category = itemView.findViewById(R.id.mealCat);
            cardView = itemView.findViewById(R.id.daymeal);
        }
    }
}
