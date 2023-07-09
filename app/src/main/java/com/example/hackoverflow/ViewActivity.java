package com.example.hackoverflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

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
                Intent searchIntent = new Intent(getBaseContext(), SearchActivity.class);
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
                Intent searchIntent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(searchIntent);
            }
        });


    }




}