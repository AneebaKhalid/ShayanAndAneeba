package com.example.foodsharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity {

    TextView pTitle, pDescription, pPrice,pTime,pType;
    ImageView pImage, pImage2;
    ViewFlipper pFlip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Post");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Flipper View
        pFlip = findViewById(R.id.viewFlipper);


        pTitle = findViewById(R.id.titlePost);
        pDescription = findViewById(R.id.descPost);
        pImage = findViewById(R.id.imagePost);
        pImage2 = findViewById(R.id.imagePost2);
        pPrice = findViewById(R.id.pricePost);
        pTime = findViewById(R.id.timePost);
        pType = findViewById(R.id.typePost);

        //Get Data from Card
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");
        String image2 = getIntent().getStringExtra("image2");
        String price = getIntent().getStringExtra("price");
        String time = getIntent().getStringExtra("time");
        String type = getIntent().getStringExtra("type");


        //Set Data in Views
        pTitle.setText(title);
        pDescription.setText(desc);
        pPrice.setText(price);
        pTime.setText(time);
        pType.setText(type);
        Picasso.get().load(image).into(pImage);
        Picasso.get().load(image2).into(pImage2);


        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);


        pFlip.setAutoStart(true);
        pFlip.setInAnimation(in);
        pFlip.setOutAnimation(out);
        pFlip.setFlipInterval(3000);


    }

    // Handle Back press

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
