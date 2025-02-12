package com.example.testtracker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.models.allcategory.Category;
import com.example.testtracker.view.interfaces.OnCaregoryClickListener;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private final static String TAG = "productClient";
    private final Context context;
    private final List<Category> categoryList;
    private final OnCaregoryClickListener listener;
    public CategoriesAdapter(List<Category> categoryList, Context context, OnCaregoryClickListener listener) {
        this.listener = listener;
        this.categoryList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_card, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.name.setText(category.getStrCategory());
        Glide.with(context).load(category.getStrCategoryThumb()).into(holder.image);
        holder.cardView.setOnClickListener(view -> listener.onCategoryClick(category.getStrCategory(),view));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public void updateData(List<Category> newProducts) {
        this.categoryList.clear();
        this.categoryList.addAll(newProducts);
        notifyDataSetChanged();
    }
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        ConstraintLayout cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.catName);
            image = itemView.findViewById(R.id.catImage);
            cardView = itemView.findViewById(R.id.catCard);

        }
    }
}

