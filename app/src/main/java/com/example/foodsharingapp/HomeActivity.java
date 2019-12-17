package com.example.foodsharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

public class HomeActivity extends AppCompatActivity {


    BottomNavigationView nav_bar;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    LinearLayoutManager mLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Today's Menu");
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLL = new LinearLayoutManager(this);
        mLL.setReverseLayout(true);
        mLL.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLL);
        //mRef = FirebaseDatabase.getInstance().getReference();
       // mRef = mFirebaseDatabase.getReference("Data");

        // Navigation Bar Grid View
        nav_bar = findViewById(R.id.nav_bar);
        nav_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.grid:
                        Intent i = new Intent(HomeActivity.this,SliderActivity.class);
                        startActivity(i);
                        return true;

                        default:
                            return false;

                }
            }
        });


    }

    //Search Bar
    private void FirebaseSearch(String searchText){
        String query = searchText.toLowerCase();
        Query searchQuery = FirebaseDatabase.getInstance().getReference("Data").orderByChild("title").startAt(query).endAt(query + "\uf0ff");

        FirebaseRecyclerOptions<MyData> searchOptions =
                new FirebaseRecyclerOptions.Builder<MyData>().setQuery(searchQuery, MyData.class).build();

        FirebaseRecyclerAdapter<MyData, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyData, ViewHolder>(searchOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MyData model) {
                holder.setDetails(getApplicationContext(),model.getTitle(),model.getImage(),model.getLogo(),model.getPrice(),model.getTime());

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);


                return null;
            }
        };
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    // Search Bar ends here

    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance().getReference("Data");
        FirebaseRecyclerAdapter<MyData, ViewHolder> firebaseRecyclerAdapter;
        FirebaseRecyclerOptions<MyData> options =
                new FirebaseRecyclerOptions.Builder<MyData>().setQuery(query, MyData.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter< MyData, ViewHolder>(options){

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MyData model) {
                holder.setDetails(getApplicationContext(),model.getTitle(),model.getImage(),model.getLogo(),model.getPrice(),model.getTime());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent,false);
                // Handle click on the recycler view
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
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }


    // Searching Query

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FirebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FirebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    } //Searching Query ends


    // Selecting Item from Search
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
