package com.example.hackoverflow.utils;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hackoverflow.R;

import java.util.List;

public class PlantAdapter extends ArrayAdapter {

    int mLayoutID;
    List<Plant> mPlants;
    Context mContext;

    public PlantAdapter(@NonNull Context context, int resource, @NonNull List<Plant> objects){
        super(context, resource, objects);
        mLayoutID = resource;
        mContext = context;
        mPlants = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View currentViewItem = convertView;

        if (currentViewItem == null){
            currentViewItem = LayoutInflater.from(getContext()).inflate(mLayoutID, parent, false);
        }

        Plant currentPlant = mPlants.get(position);

        if (mLayoutID == R.layout.top_country_item){
            ImageView iconImageView = (ImageView) currentViewItem.findViewById(R.id.top_country_icon);
            int i = mContext.getResources().getIdentifier(
                    currentPlant.getImage(), "drawable", mContext.getPackageName()
            );

            iconImageView.setImageResource(i);

            TextView countryName = (TextView)currentViewItem.findViewById(R.id.top_country_name);
            countryName.setText((currentPlant.getName()));

            currentViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewActivity.byCountry = 1;
                    ViewActivity.byCountryString = currentPlant.getName();
                    Intent detailsIntent = new Intent(mContext, ViewActivity.class);
                    mContext.startActivity(detailsIntent);
                }
            });
        }
        else {
            ImageView iconImageView = (ImageView) currentViewItem.findViewById(R.id.plant_icon);
            int i = mContext.getResources().getIdentifier(
                    currentPlant.getImage(), "drawable", mContext.getPackageName()
            );

            iconImageView.setImageResource(i);

            TextView countryName = (TextView)currentViewItem.findViewById(R.id.plant_name);
            countryName.setText((currentPlant.getName()));

            currentViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewActivity.byPlant = 1;
                    ViewActivity.byPlantString = currentPlant.getName();
                    Intent intent = new Intent(mContext, ViewActivity.class);
                    intent.putExtra("plantName", currentPlant.getName());
                    intent.putExtra("plantImage", currentPlant.getImage());
                    intent.putExtra("plantDescription", currentPlant.getDescription());
                    intent.putExtra("plantBookmark", currentPlant.getBookmark());
                    mContext.startActivity(intent);
                }
            });
        }

        return currentViewItem;
    }
}