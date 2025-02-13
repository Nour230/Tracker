package com.example.testtracker.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testtracker.R;
import com.example.testtracker.models.allcountries.Country;
import com.example.testtracker.view.interfaces.OnContryClickListener;

import java.util.List;

public class countriesAdapter extends BaseAdapter {
    private final List<Country>cuntryList;
    private final Context context;;
    private final OnContryClickListener listener;
    public countriesAdapter(List<Country> cuntryList, Context context, OnContryClickListener listener) {
        this.listener = listener;
        this.cuntryList = cuntryList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cuntryList.size();
    }

    @Override
    public Object getItem(int position) {
        return cuntryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
           convertView = View.inflate(context,
                   R.layout.country_card, null);
           holder = new ViewHolder();
           holder.name = convertView.findViewById(R.id.countryName);
           convertView.setTag(holder);

       }
       else{
            holder = (ViewHolder) convertView.getTag();
           holder.name.setText(cuntryList.get(position).getStrArea());
       }
       Country country = cuntryList.get(position);
       holder.name.setText(country.getStrArea());
       convertView.setOnClickListener(view -> listener.onContryClick(country.getStrArea(),view));
        return convertView;
    }
    public void updateData(List<Country> newProducts) {
        this.cuntryList.clear();
        this.cuntryList.addAll(newProducts);
        notifyDataSetChanged();
    }
    private static class ViewHolder {
        TextView name;
    }
}
