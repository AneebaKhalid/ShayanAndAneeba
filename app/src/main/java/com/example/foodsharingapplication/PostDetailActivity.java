package com.example.foodsharingapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class PostDetailActivity extends AppCompatActivity {

    TextView pTitle, pDescription, pPrice,pTime,pType, pCuisineType, pAvailable,pPayment;
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
        //pImage = findViewById(R.id.imagePost);
        //pImage2 = findViewById(R.id.imagePost2);
        pPrice = findViewById(R.id.pricePost);
        pTime = findViewById(R.id.timePost);
        pType = findViewById(R.id.typePost);
        pAvailable = findViewById(R.id.availability);
        pCuisineType = findViewById(R.id.cuisineTypePost);
        pPayment = findViewById(R.id.paymentPost);

        //Get Data from Card
        Intent intent = getIntent();
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");
       // String image2 = getIntent().getStringExtra("image2");
        String price = getIntent().getStringExtra("price");
        String time = getIntent().getStringExtra("time");
        String type = getIntent().getStringExtra("type");
        String availablity = getIntent().getStringExtra("availability");
        String cuisineType = getIntent().getStringExtra("cuisineType");
        String payment = getIntent().getStringExtra("pay");

        ArrayList<String> mArray = (ArrayList<String>)intent.getSerializableExtra("arrayImage");



        //Set Data in Views
        pTitle.setText(title);
        pDescription.setText(desc);
        pPrice.setText(price);
        pTime.setText(time);
        pType.setText(type);
        pAvailable.setText(availablity);
        pCuisineType.setText(cuisineType);
        pPayment.setText(payment);


        for (int i = 0; i < mArray.size(); i++) {

            String uri = mArray.get(i);
            ImageView imageV = new ImageView(this);
            Picasso.get().load(uri).into(imageV);

            // Adds the view to the layout
            pFlip.addView(imageV);
        }



        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);

        // Adding animation to the image flip
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
