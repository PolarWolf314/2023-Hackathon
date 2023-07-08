package com.example.hackoverflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button getStartedButton = findViewById(R.id.mainGetStartedButton);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getStartedButton.setOnClickListener(v -> {
            // Handle button click event
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


}