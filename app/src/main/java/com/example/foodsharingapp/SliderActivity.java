package com.example.foodsharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class SliderActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    BottomNavigationView nav_bar;
    RecyclerView myRecyclerView2;
    FirebaseDatabase myFirebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        // ACTION BAR.....
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Today's Menu");

        //Recycler View Find by ID
        myRecyclerView = findViewById(R.id.slider_recycler_view);
        myRecyclerView.setHasFixedSize(true);
        //myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myRecyclerView2 = findViewById(R.id.slider2_recycler_view);
        myRecyclerView2.setHasFixedSize(true);


        // Make it Horizontal
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myRecyclerView.setLayoutManager(layoutManager);
        myRef = FirebaseDatabase.getInstance().getReference(); //Database Object

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL , false);
        myRecyclerView2.setLayoutManager(layoutManager2);
        //myRef = FirebaseDatabase.getInstance().getReference(); //Database Object





        // Navigation Bar Grid View
        nav_bar = findViewById(R.id.nav_bar);
        nav_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.grid:
                        Intent i = new Intent(SliderActivity.this,HomeActivity.class);
                        startActivity(i);
                        return true;

                    default:
                        return false;

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Query for Recycler View 1
       // Query query = myRef.child("Data");
        Query query = FirebaseDatabase.getInstance().getReference("Data");
        FirebaseRecyclerOptions<MyData> options =
                new FirebaseRecyclerOptions.Builder<MyData>().setQuery(query, MyData.class).build();

        FirebaseRecyclerAdapter<MyData, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyData, ViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MyData model) {
                holder.setDetails(getApplicationContext(), model.getTitle(), model.getImage(), model.getLogo(),model.getPrice(),model.getTime());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid, parent, false);

                ViewHolder viewHolder = new ViewHolder(view);

                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String myTitle = getItem(position).getTitle();
                        String myDesc = getItem(position).getDescription();
                        String myImage = getItem(position).getImage();
                        String myPrice = getItem(position).getPrice();
                        String myTime = getItem(position).getTime();
                        String myType = getItem(position).getType();
                        String myImage2 = getItem(position).getImage2();


                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        intent.putExtra("image", myImage);
                        intent.putExtra("title", myTitle);
                        intent.putExtra("description", myDesc);
                        intent.putExtra("price", myPrice);
                        intent.putExtra("time", myTime);
                        intent.putExtra("type", myType);
                        intent.putExtra("image2", myImage2);

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                return viewHolder;
            }
        };


        //Query for Recycler View 2
        //Query query2 = myRef.child("Data");
        Query query2 = FirebaseDatabase.getInstance().getReference("Data");
        FirebaseRecyclerAdapter<MyData, ViewHolder> firebaseRecyclerAdapter2;
        FirebaseRecyclerOptions<MyData> options2 =
                new FirebaseRecyclerOptions.Builder<MyData>().setQuery(query2, MyData.class).build();

        firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter< MyData, ViewHolder>(options2){

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MyData model) {
                holder.setDetails(getApplicationContext(),model.getTitle(),model.getImage(),model.getLogo(),model.getPrice(),model.getTime());
                holder.mView.findViewById(R.id.fav).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageButton favImage = findViewById(R.id.fav);
                        favImage.setImageResource(R.drawable.ic_favorite_black_24dp);
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid, parent,false);

                ViewHolder viewHolder2 = new ViewHolder(view);
                viewHolder2.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String myTitle = getItem(position).getTitle();
                        String myDesc = getItem(position).getDescription();
                        String myImage = getItem(position).getImage();
                        String myPrice = getItem(position).getPrice();
                        String myTime = getItem(position).getTime();
                        String myType = getItem(position).getType();
                        String myImage2 = getItem(position).getImage2();


                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        intent.putExtra("image", myImage);
                        intent.putExtra("title", myTitle);
                        intent.putExtra("description", myDesc);
                        intent.putExtra("price", myPrice);
                        intent.putExtra("time", myTime);
                        intent.putExtra("type", myType);
                        intent.putExtra("image2", myImage2);

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                return viewHolder2;
            }
        };
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter2.startListening();
        myRecyclerView.setAdapter(firebaseRecyclerAdapter);
        myRecyclerView2.setAdapter(firebaseRecyclerAdapter2);

    }


}
