package com.example.foodsharingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SliderActivity extends AppCompatActivity {

    RecyclerView myRecyclerView,sRecyclerView;
    BottomNavigationView nav_bar;
    RecyclerView myRecyclerView2;
    LinearLayout slider_linear_layout;
    FirebaseDatabase myFirebaseDatabase;
    DatabaseReference myRef;
    UploadModel uploadModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        // ///////ACTION BAR////////////
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Today's Menu");

        // ///////Recycler View Find by ID//////////////////
        slider_linear_layout = findViewById(R.id.sliders_linear_layout);

        myRecyclerView = findViewById(R.id.slider_recycler_view);
        myRecyclerView.setHasFixedSize(true);

        myRecyclerView2 = findViewById(R.id.slider2_recycler_view);
        myRecyclerView2.setHasFixedSize(true);


        sRecyclerView = findViewById(R.id.search_recycler_view);
        sRecyclerView.setHasFixedSize(true);
        LinearLayoutManager LL = new LinearLayoutManager(this);
        sRecyclerView.setLayoutManager(LL);



        // ////////Make it Horizontal/////////////

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myRecyclerView.setLayoutManager(layoutManager);
        myRef = FirebaseDatabase.getInstance().getReference();
        //Database Reference

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL , false);
        myRecyclerView2.setLayoutManager(layoutManager2);



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

                        // Image Setting
                        intent.putExtra("arrayImage", imagesArray);

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
        sRecyclerView.setAdapter(firebaseRecyclerAdapter);
        // ///////Search View ends here/////////
    } // firebase Search ends here

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
                slider_linear_layout.setVisibility(View.GONE);
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    } // ///////Searching Query functions////////////////


    // //////Selecting Setting Options//////
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //  //////////// SEARCHING ENDS here//////////

        // //////////////Query and Populating Recycler View 1 ///////////

        Query query = FirebaseDatabase.getInstance().getReference("Food").child("FoodByAllUsers");
        FirebaseRecyclerOptions<UploadModel> options =
                new FirebaseRecyclerOptions.Builder<UploadModel>().setQuery(query, UploadModel.class).build();

        FirebaseRecyclerAdapter<UploadModel, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UploadModel, ViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UploadModel model) {
                holder.setDetails(getApplicationContext(),model.getFoodTitle(),model.getmArrayString(),model.getUser().getUserProfilePicUrl(),model.getFoodPrice(),model.getFoodPickUpDetail());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid, parent, false);

                ViewHolder viewHolder = new ViewHolder(view);

                // ////////// Handling Click on Slider View 1 /////////////////

                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String myTitle = getItem(position).getFoodTitle();
                        String myDesc = getItem(position).getFoodDescription();
                        String myImage = getItem(position).getmImageUri();
                        String myPrice = getItem(position).getFoodPrice();
                        String myTime = getItem(position).getFoodPickUpDetail();
                        String myType = getItem(position).getFoodType();
                        String myCuisineType = getItem(position).getFoodTypeCuisine();
                        String pay = getItem(position).getPayment();
                        String available = getItem(position).getAvailabilityDays();

                        ArrayList<String> imagesArray = getItem(position).getmArrayString();


                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        intent.putExtra("image", myImage);
                        intent.putExtra("title", myTitle);
                        intent.putExtra("description", myDesc);
                        intent.putExtra("price", myPrice);
                        intent.putExtra("time", myTime);
                        intent.putExtra("type", myType);
                        intent.putExtra("cuisineType",myCuisineType);
                        intent.putExtra("pay", pay);
                        intent.putExtra("availability",available);
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


        // //////////////Query and Populating Slider Recycler View 2 ///////////

        Query query2 = FirebaseDatabase.getInstance().getReference("Food").child("FoodByAllUsers");
        FirebaseRecyclerAdapter<UploadModel, ViewHolder> firebaseRecyclerAdapter2;
        FirebaseRecyclerOptions<UploadModel> options2 =
                new FirebaseRecyclerOptions.Builder<UploadModel>().setQuery(query2, UploadModel.class).build();

        firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter< UploadModel, ViewHolder>(options2){

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull UploadModel model) {
                holder.setDetails(getApplicationContext(),model.getFoodTitle(),model.getmArrayString(),model.getUser().getUserProfilePicUrl(),model.getFoodPrice(),model.getFoodPickUpDetail());
                holder.itemView.findViewById(R.id.fav).setOnClickListener(new View.OnClickListener() {

                    String title = getItem(position).getFoodTitle();
                    Query q = FirebaseDatabase.getInstance().getReference("Food").child("FoodByAllUsers").orderByChild("foodTitle");
                    Query favorites = q.equalTo(title);
                    @Override
                    public void onClick(View v) {
                        //UploadModel uploadModel;
                        if(favorites==null){

                        }
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "This is a message displayed in a Toast",
                                Toast.LENGTH_SHORT);
                        toast.show();

                        ImageButton favImage = v.findViewById(R.id.fav);
                        favImage.getId();
                        favImage.setImageResource(R.drawable.ic_favorite_black_24dp);
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid, parent,false);

                // ////////// Handling Click on Slider View 2 /////////////////

                ViewHolder viewHolder2 = new ViewHolder(view);
                viewHolder2.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String myTitle = getItem(position).getFoodTitle();
                        String myDesc = getItem(position).getFoodDescription();
                        String myImage = getItem(position).getmImageUri();
                        String myPrice = getItem(position).getFoodPrice();
                        String myTime = getItem(position).getFoodPickUpDetail();
                        String myType = getItem(position).getFoodType();
                        String myCuisineType = getItem(position).getFoodTypeCuisine();
                        String pay = getItem(position).getPayment();
                        String available = getItem(position).getAvailabilityDays();
                        ArrayList<String> imagesArray = getItem(position).getmArrayString();


                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        intent.putExtra("image", myImage);
                        intent.putExtra("title", myTitle);
                        intent.putExtra("description", myDesc);
                        intent.putExtra("price", myPrice);
                        intent.putExtra("time", myTime);
                        intent.putExtra("type", myType);
                        intent.putExtra("cuisineType",myCuisineType);
                        intent.putExtra("pay", pay);
                        intent.putExtra("availability",available);
                        intent.putExtra("arrayImage", imagesArray);


                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "This is a message displayed in a Toast",
                                Toast.LENGTH_SHORT);
                        toast.show();
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
