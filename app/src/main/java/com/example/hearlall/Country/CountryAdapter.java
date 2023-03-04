package com.example.hearlall.Country;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hearlall.Country.Country;
import com.example.hearlall.R;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private final Context context;
    private final List<Country> countryList;

    public CountryAdapter(Context context, List<Country> countryList)
    {
        this.context = context;
        this.countryList = countryList;
    }

    @Override
    public int getCount() {
        return countryList != null ? countryList.size() :0;
    }

    @Override
    public Object getItem(int i) {
        String country = countryList.get(i).getName();
        return country;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_country, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);

        txtName.setText(countryList.get(i).getName());

        return rootView;
    }
}
