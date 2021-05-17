package com.nikunj.statussaver.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.nikunj.statussaver.R;

import java.io.File;

public class ImageView extends AppCompatActivity {
    public android.widget.ImageView myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent intent=getIntent();
       String file1= intent.getStringExtra("file");
       File file=new File(file1);

        if(file.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

           myImage = findViewById(R.id.image_view1);

            myImage.setImageBitmap(myBitmap);

        }
    }
}