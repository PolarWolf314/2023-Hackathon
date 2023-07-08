package com.example.hackoverflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookmarkActivity extends AppCompatActivity {

    class ViewHolder {
        ListView favouritesHolder;
        LinearLayout emptyFavouritesHolder;

        public ViewHolder() {

            favouritesHolder = findViewById(R.id.favourites_holder);
            emptyFavouritesHolder = findViewById(R.id.emptyFavouritesView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        ImageView navbarHomeButton = (ImageView) findViewById(R.id.navbarHomeButton);
        navbarHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        ImageView navbarSearchButton = (ImageView) findViewById(R.id.navbarSearchButton);
        navbarSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent searchIntent = new Intent(getBaseContext(), BookmarkActivity.class);
                startActivity(searchIntent);
            }
        });

        ImageView navbarBookmarkButton = (ImageView) findViewById(R.id.navbarBookmarkButton);
        navbarBookmarkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent homeIntent = new Intent(getBaseContext(), BookmarkActivity.class);
                startActivity(homeIntent);
            }
        });

        ImageView navbarMenuButton = (ImageView) findViewById(R.id.navbarMenuButton);
        navbarMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent searchIntent = new Intent(getBaseContext(), BookmarkActivity.class);
                startActivity(searchIntent);
            }
        });
    }
}