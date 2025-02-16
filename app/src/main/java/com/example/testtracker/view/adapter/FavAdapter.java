package com.example.testtracker.view.adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.testtracker.view.interfaces.OnMealClickListener;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MealViewHolder> {
    private Context context;
    private List<SavedMeals> mealsList;
    private OnMealClickListener listener;

    public FavAdapter(Context context, List<SavedMeals> mealsList, OnMealClickListener listener) {
        this.context = context;
        this.mealsList = mealsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        SavedMeals meal = mealsList.get(position);
        holder.name.setText("Name: " + meal.getMeal().getStrMeal());
        holder.area.setText("Area: " + meal.getMeal().getStrArea());
        holder.category.setText("Category: " + meal.getMeal().getStrCategory());
        Glide.with(context).load(meal.getMeal().getStrMealThumb()).into(holder.image);
        holder.deletFromFav.setOnClickListener(v -> {
            listener.deleteFromFav(meal);
            mealsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mealsList.size());
        });
    }

    @Override
    public int getItemCount() {
        return mealsList != null ? mealsList.size() : 0;
    }

    public void updateData(List<SavedMeals> newMeals) {
        this.mealsList = newMeals;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView name, area, category;
        ImageView image;
        MaterialButton deletFromFav;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mealName);
            image = itemView.findViewById(R.id.mealImage);
            area = itemView.findViewById(R.id.mealArea);
            category = itemView.findViewById(R.id.mealCat);
            deletFromFav = itemView.findViewById(R.id.addtofav);
        }
    }
}