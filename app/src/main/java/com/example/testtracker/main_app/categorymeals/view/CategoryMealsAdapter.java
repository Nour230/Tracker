package com.example.testtracker.main_app.categorymeals.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.main_app.categorymeals.model.CategoryMeals;

import java.util.List;

public class CategoryMealsAdapter extends RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder> {
    private final static String TAG = "productClient";
    private final Context context;
    private final List<CategoryMeals> categoryMealsList;
    private final OnMealClickListener listener;
    public CategoryMealsAdapter(List<CategoryMeals> categoryMealsList, Context context, OnMealClickListener listener) {
        this.listener = listener;
        this.categoryMealsList = categoryMealsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_meals, parent, false);
        return new CategoryMealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryMealsAdapter.CategoryMealsViewHolder holder, int position) {
        CategoryMeals categoryMeals = categoryMealsList.get(position);
        holder.name.setText(categoryMeals.getStrMeal());
        Glide.with(context).load(categoryMeals.getStrMealThumb()).into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            if (categoryMeals != null && listener != null) {
                listener.onMealClick(categoryMeals.getIdMeal(), v); // Ensure getIdMeal() returns the correct ID
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryMealsList.size();
    }
    public void updateData(List<CategoryMeals> newProducts) {
        this.categoryMealsList.clear();
        this.categoryMealsList.addAll(newProducts);
        notifyDataSetChanged();
    }
    public static class CategoryMealsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        Button fav;
        public CategoryMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.catMealName);
            image = itemView.findViewById(R.id.catMealImage);
            fav = itemView.findViewById(R.id.addtofav);
        }
    }
}
