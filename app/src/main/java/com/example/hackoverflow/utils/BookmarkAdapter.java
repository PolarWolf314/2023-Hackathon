package com.example.hackoverflow.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hackoverflow.R;

import java.util.List;

public class BookmarkAdapter extends ArrayAdapter<Plant> {

    private List<Plant> mPlant;
    private int mLayoutID;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public BookmarkAdapter(@NonNull Context context, int resource, @NonNull List<Plant> objects) {
        super(context, resource, objects);
        mLayoutID = resource;
        mPlant = objects;
        mContext = context;
    }

    public interface OnItemClickListener {
        void onItemClick(Plant item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private static class ViewHolder {
        LinearLayout favouritesHolder;
        ImageView favouritesItemImage;
        TextView favouritesItemDescription;
        ImageButton removeFromFavourites;

        public ViewHolder(View currentListViewItem) {
            favouritesItemImage = currentListViewItem.findViewById(R.id.favourites_plant _image);
            favouritesItemDescription = currentListViewItem.findViewById(R.id.favourites_plant_description);
            removeFromFavourites = currentListViewItem.findViewById(R.id.favouritesRemoveButton);
            favouritesHolder = currentListViewItem.findViewById(R.id.favourites_holder);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(mLayoutID, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Plant currentItem = mPlant.get(position);

        ImageView iconImageView = viewHolder.favouritesItemImage;
        int resourceId = mContext.getResources().getIdentifier(currentItem.getImage(), "drawable", mContext.getPackageName());
        iconImageView.setImageResource(resourceId);

        TextView listingDescription = viewHolder.favouritesItemDescription;
        listingDescription.setText(currentItem.getName());

        viewHolder.removeFromFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem.setBookmark(false);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(currentItem);
                }
            }
        });

        return convertView;
    }
}
