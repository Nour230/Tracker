package com.example.testtracker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.models.allcategory.Category;
import com.example.testtracker.models.allcountries.Country;
import com.example.testtracker.models.mealdetails.AllIngrediants;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private final Context context;
    private final List<?> items;

    public SearchAdapter(Context context, List<?> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the search_card layout
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_card, parent, false);
        }
        convertView.setFocusable(false);
        convertView.setClickable(false);

        TextView textView = convertView.findViewById(R.id.chipName);
        ImageView imageView = convertView.findViewById(R.id.chipImage);
        Object item = items.get(position);

        if (item instanceof AllIngrediants.Ingrediants) {
            AllIngrediants.Ingrediants ingredient = (AllIngrediants.Ingrediants) item;
            textView.setText(ingredient.getStrIngredient());

            // Load the ingredient image using Glide
            String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + "-Small.png";
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.searchfood) // Placeholder image while loading
                    .error(R.drawable.searchfood) // Image to display if loading fails
                    .into(imageView);

        } else if (item instanceof Category) {
            textView.setText(((Category) item).getStrCategory());
            imageView.setImageResource(R.drawable.searchfood);
        } else if (item instanceof Country) {
            Country country = (Country) item;
            textView.setText(country.getStrArea());

            // Load the country flag using Glide
            String flagUrl = "https://www.themealdb.com/images/icons/flags/big/64/" +
                    country.getStrArea().toLowerCase() + ".png";
            Glide.with(context)
                    .load(flagUrl)
                    .placeholder(R.drawable.worldmap) // Placeholder image while loading
                    .error(R.drawable.worldmap) // Image to display if loading fails
                    .into(imageView);
        }

        return convertView;
    }
}