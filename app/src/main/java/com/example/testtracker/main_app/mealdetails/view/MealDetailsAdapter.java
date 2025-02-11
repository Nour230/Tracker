package com.example.testtracker.main_app.mealdetails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtracker.R;
import com.example.testtracker.db.MealDAO;
import com.example.testtracker.main_app.mealdetails.model.AllIngrediants;

import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.MealDetailsViewHolder> {
    private final static String TAG = "productClient";
    private final Context context;
    private final List<AllIngrediants.Ingrediants> ingrediantList;

    public MealDetailsAdapter(Context context, List<AllIngrediants.Ingrediants> categoryList) {
        this.context = context;
        this.ingrediantList = categoryList;
    }

    public void updateData(List<AllIngrediants.Ingrediants> newProducts) {
        this.ingrediantList.clear();
        this.ingrediantList.addAll(newProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_ingrediant, parent, false);
        return new MealDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealDetailsViewHolder holder, int position) {
        AllIngrediants.Ingrediants meal = ingrediantList.get(position);
        holder.ingredients.setText(meal.getStrIngredient());
        holder.measure.setText(meal.getIdIngredient());
    }

    @Override
    public int getItemCount() {
        return ingrediantList.size();
    }

    public static class MealDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView ingredients, measure;
        ImageView image;

        public MealDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredients = itemView.findViewById(R.id.ingredName);
            measure = itemView.findViewById(R.id.ingredMesure);
            image = itemView.findViewById(R.id.ingredImage);

        }
    }
}
