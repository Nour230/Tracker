package com.example.testtracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.network.Meal;

import java.util.List;

public class DailyAdaoter extends RecyclerView.Adapter<DailyAdaoter.MealViewHolder> {
    private final static String TAG = "productClient";
    private final Context context;
    private final List<Meal> dailyList;

    public DailyAdaoter(List<Meal> dailyList, Context context) {
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
        holder.name.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }
    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mealName);
            image = itemView.findViewById(R.id.mealImage);
        }
    }
}
