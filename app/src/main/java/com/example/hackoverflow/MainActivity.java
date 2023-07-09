package com.example.hackoverflow;

import androidx.activity.result.ActivityResult;

import androidx.activity.result.ActivityResultCallback;

import androidx.activity.result.ActivityResultCaller;

import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;


import android.app.Activity;

import android.content.ContentResolver;

import android.content.Intent;

import android.graphics.Bitmap;

import android.media.Image;

import android.net.Uri;

import android.os.Bundle;

import android.os.Environment;

import android.provider.MediaStore;

import android.util.Log;

import android.util.Rational;

import android.util.Size;

import android.view.TextureView;

import android.view.View;

import android.widget.Button;

import android.widget.ImageView;

import android.widget.Toast;


import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 22;
    public static int ITERATOR = 1;

    Button btnpicture, upldpicture;

    ImageView imageView;

    ActivityResultLauncher<Intent> activityResultLauncher;

    public Uri getURI(MainActivity context, File file) {
        return FileProvider.getUriForFile(
                context.getApplicationContext(),
                "com.example.hackoverflow.provider",
                new File(context.getCacheDir(), "images/" + file.getName())
        );
    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnpicture = findViewById(R.id.mainGetStartedButton);

        upldpicture = findViewById(R.id.mainUploadPhotoButton);

        imageView = findViewById(R.id.mainCameraImage);


        btnpicture.setOnClickListener(v -> {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            activityResultLauncher.launch(cameraIntent);


        });


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {


            Bundle extras = result.getData().getExtras();

            Uri imageUri;

            Bitmap imageBitmap = (Bitmap) extras.get("data");

            WeakReference<Bitmap> result_1 = new WeakReference<>(Bitmap.createScaledBitmap(imageBitmap,

                            imageBitmap.getWidth(), imageBitmap.getHeight(), false).

                    copy(Bitmap.Config.RGB_565, true));


            Bitmap bm = result_1.get();


            imageUri = saveImage(bm, MainActivity.this);
            System.out.println("ImageUri from toString() is " + imageUri.toString());
            System.out.println("ImageUri from getPath() is " + imageUri.getPath());


            File imagefolder = new File(MainActivity.this.getCacheDir(), "images");
            File file = new File(imagefolder, ITERATOR + "captured_image.jpg");

            api.identifyPlant(file, this);
            imageView.setImageURI(imageUri);

            ITERATOR++;
        });


    }


    private Uri saveImage(Bitmap image, MainActivity context) {

        File imagefolder = new File(context.getCacheDir(), "images");
        System.out.println(imagefolder);

        Uri uri = null;

        try {

            if (!imagefolder.exists() && !imagefolder.isDirectory()) {
                imagefolder.mkdirs();
            }

            File file = new File(imagefolder, ITERATOR + "captured_image.jpg");

            FileOutputStream stream = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            stream.flush();

            stream.close();

            uri = getURI(context, file);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return uri;

    }


}
