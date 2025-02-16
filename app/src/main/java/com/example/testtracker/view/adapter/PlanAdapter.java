package com.example.testtracker.view.adapter;

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
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.view.interfaces.OnMealClickListener;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MealViewHolder> {
    private Context context;
    private List<MealDetails.MealsDTO> mealsList;

    public PlanAdapter(Context context, List<MealDetails.MealsDTO> mealsList) {
        this.context = context;
        this.mealsList = mealsList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealDetails.MealsDTO meal = mealsList.get(position);
        holder.name.setText("Name: " + meal.getStrMeal());
        holder.area.setText("Area: " + meal.getStrArea());
        holder.category.setText("Category: " + meal.getStrCategory());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mealsList != null ? mealsList.size() : 0;
    }

    public void updateData(List<MealDetails.MealsDTO> newMeals) {
        this.mealsList = newMeals;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView name, area, category;
        ImageView image;


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.planmealName);
            image = itemView.findViewById(R.id.planmealImage);
            area = itemView.findViewById(R.id.planmealArea);
            category = itemView.findViewById(R.id.planmealCat);

        }
    }

}
