package com.example.testtracker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        // Inflate the category_card layout
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_card, parent, false);
        }
        convertView.setFocusable(false);
        convertView.setClickable(false);

        TextView textView = convertView.findViewById(R.id.chipName);
        ImageView imageView = convertView.findViewById(R.id.chipImage);
        Object item = items.get(position);

        if (item instanceof AllIngrediants.Ingrediants) {
            textView.setText(((AllIngrediants.Ingrediants) item).getStrIngredient());
            imageView.setImageResource(R.drawable.searchfood);
        } else if (item instanceof Category) {
            textView.setText(((Category) item).getStrCategory());
            imageView.setImageResource(R.drawable.searchfood);
        } else if (item instanceof Country) {
            textView.setText(((Country) item).getStrArea());
            imageView.setImageResource(R.drawable.worldmap);
        }

        return convertView;
    }
}