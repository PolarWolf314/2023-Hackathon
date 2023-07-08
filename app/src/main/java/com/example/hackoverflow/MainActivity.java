package com.example.hackoverflow;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
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

    Button btnpicture, upldpicture;

    ImageView imageView;

    int SELECT_PICTURE = 200;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnpicture = findViewById(R.id.mainGetStartedButton);
        upldpicture = findViewById(R.id.mainUploadPhotoButton);
        imageView = findViewById(R.id.mainCameraImage);

        btnpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(cameraIntent);
            }
        });

        upldpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            if (extras != null) {
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                WeakReference<Bitmap> result_1 = new WeakReference<>(Bitmap.createScaledBitmap(
                                        imageBitmap, imageBitmap.getWidth(), imageBitmap.getHeight(), false
                                ).copy(Bitmap.Config.RGB_565, true));
                                Bitmap bm = result_1.get();
                                Uri imageUri = saveImage(bm, MainActivity.this);
                                imageView.setImageURI(imageUri);
                            }
                        }
                    }
                });
    }

    private Uri saveImage(Bitmap image, MainActivity context) {
        File imagefolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "captured_image.jpg");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context, "com.example.hackoverflow.provider", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
