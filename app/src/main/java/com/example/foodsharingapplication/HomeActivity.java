package com.example.foodsharingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {


    BottomNavigationView nav_bar;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    LinearLayoutManager mLL;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseAuth.getCurrentUser();
        if (!mFirebaseAuth.getCurrentUser().equals(null)){

        }
        // ////////Action Bar////////////
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Today's Menu");
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLL = new LinearLayoutManager(this);

        // ///// Set Latest First ////////////
        mLL.setReverseLayout(true);
        mLL.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLL);

        // ////////Navigation Bar Grid View//////////////
        nav_bar = findViewById(R.id.nav_bar);
        nav_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.grid:
                        Intent i = new Intent(HomeActivity.this,SliderActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.add:
                        Intent uploadIntent = new Intent(HomeActivity.this,UploadData.class);
                        startActivity(uploadIntent);
                        return true;
                    // ///// ADD more cases for different navigation bar options////////
                        default:
                            return false;

                }
            }
        });


    }

    // /////////Search View Query and Populating View//////////
    private void firebaseSearch(String searchText){
        String query = searchText;
        Query searchQuery = FirebaseDatabase.getInstance().getReference("Food").child("FoodByAllUsers").orderByChild("foodTitle").startAt(query).endAt(query + "\uf0ff");

        FirebaseRecyclerOptions<UploadModel> searchOptions =
                new FirebaseRecyclerOptions.Builder<UploadModel>().setQuery(searchQuery, UploadModel.class).build();

        FirebaseRecyclerAdapter<UploadModel, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter< UploadModel, ViewHolder>(searchOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UploadModel model) {
                holder.setDetails(getApplicationContext(),model.getFoodTitle(),model.getmArrayString(),model.getUser().getUserProfilePicUrl(),model.getFoodPrice(),model.getFoodPickUpDetail());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View viewSearch = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent,false);
                ViewHolder viewHolderS = new ViewHolder(viewSearch);

                // ///////On click handled for Search View to Detail Page/////////////////
                viewHolderS.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String myTitle = getItem(position).getFoodTitle();
                        String myDesc = getItem(position).getFoodDescription();
                        String myPrice = getItem(position).getFoodPrice();
                        String myTime = getItem(position).getFoodPickUpDetail();
                        String myType = getItem(position).getFoodType();
                        String myCuisineType = getItem(position).getFoodTypeCuisine();
                        String pay = getItem(position).getPayment();
                        String available = getItem(position).getAvailabilityDays();

                        // Image setting
                        //String myImage2 = getItem(position).getImage2();
                        String myImage = getItem(position).getmImageUri();
                        HashMap<String,String> hashImage = getItem(position).getHashMap();

                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);

                        intent.putExtra("title", myTitle);
                        intent.putExtra("description", myDesc);
                        intent.putExtra("price", myPrice);
                        intent.putExtra("time", myTime);
                        intent.putExtra("type", myType);
                        intent.putExtra("cuisineType",myCuisineType);
                        intent.putExtra("pay", pay);
                        intent.putExtra("availability",available);

                        // Image Setting
                        //intent.putExtra("image2", myImage2);
                        if(myImage!=null) {
                            intent.putExtra("image", myImage);
                        }
                        else if (hashImage!=null){
                            intent.getStringArrayExtra("hashImage");
                        }

                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });


                return viewHolderS;
            }
        };
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        // ///////Search View ends here/////////
    } // onCreate ends here


    @Override
    protected void onStart() {
        super.onStart();

        // ///////////Query to get Data from Firebase and Populate HomePage///////////

        Query query = FirebaseDatabase.getInstance().getReference("Food").child("FoodByAllUsers");
        FirebaseRecyclerAdapter<UploadModel, ViewHolder> firebaseRecyclerAdapter;
        FirebaseRecyclerOptions<UploadModel> options =
                new FirebaseRecyclerOptions.Builder<UploadModel>().setQuery(query, UploadModel.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter< UploadModel, ViewHolder>(options){

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UploadModel model) {

                    holder.setDetails(getApplicationContext(), model.getFoodTitle(), model.getmArrayString(), model.getUser().getUserProfilePicUrl(), model.getFoodPrice(), model.getFoodPickUpDetail());

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent,false);

                // //////Handle click on the recycler view/////////////
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(HomeActivity.this, "Sending hash image", Toast.LENGTH_SHORT).show();

                        String myTitle = getItem(position).getFoodTitle();
                        String myDesc = getItem(position).getFoodDescription();
                        String myPrice = getItem(position).getFoodPrice();
                        String myTime = getItem(position).getFoodPickUpDetail();
                        String myType = getItem(position).getFoodType();
                        String myCuisineType = getItem(position).getFoodTypeCuisine();
                        String pay = getItem(position).getPayment();
                        String available = getItem(position).getAvailabilityDays();

                        // Image setting
                        ArrayList<String> imagesArray = getItem(position).getmArrayString();

                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);

                        intent.putExtra("title", myTitle);
                        intent.putExtra("description", myDesc);
                        intent.putExtra("price", myPrice);
                        intent.putExtra("time", myTime);
                        intent.putExtra("type", myType);
                        intent.putExtra("cuisineType",myCuisineType);
                        intent.putExtra("pay", pay);
                        intent.putExtra("availability",available);
                        // Images Array List
                        intent.putExtra("arrayImage", imagesArray);


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


    // ///////// Inflating View for Search Bar /////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // ////////// Functions for Searching ///////////
            @Override
            public boolean onQueryTextSubmit(String query) {

                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    } // ///////Searching Query ends////////////////


    // //////Selecting Setting Options//////
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
