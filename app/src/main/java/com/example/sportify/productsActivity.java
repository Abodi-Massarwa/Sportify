package com.example.sportify;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportify.databinding.ActivityProductsBinding;
import com.example.sportify.ui.AdminActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class productsActivity extends AppCompatActivity {
    RecyclerView recView;
    DatabaseReference databaseReference;
    productAdapter adapter;
    private Toolbar toolbar;
    private ActivityProductsBinding binding;
    FirebaseRecyclerOptions<ProductDetails> options;
    private String category;
    private String status;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.list_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_logo);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.activityProductsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(productsActivity.this, AdminActivity.class);
                startActivity(i);
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        recView = findViewById(R.id.recView);
        Bundle extras = getIntent().getExtras();
         category = extras.getString("Category");
         status = extras.getString("Status");
        recView.setLayoutManager(new LinearLayoutManager(this));
        if (category != null) {
             options =
                     new FirebaseRecyclerOptions.Builder<ProductDetails>()
                             .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("details").equalTo(category), ProductDetails.class)
                             .build();
         }
        if (status != null){
            options =
                    new FirebaseRecyclerOptions.Builder<ProductDetails>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), ProductDetails.class)
                            .build();
        }
        adapter = new productAdapter(options);
        recView.setAdapter(adapter);
       // binding.bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
         //   @Override
           // public void onNavigationItemReselected(@NonNull MenuItem item) {
             //   switch (item.getItemId()){
                //    case R.id.profile:
               //        Intent j = new Intent(productsActivity.this,ProfileActivity.class);
                 //       startActivity(j);

                //}
            //}
        //});

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_button2).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBarFirebase(query);
                 return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBarFirebase(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void searchBarFirebase(String newText){
        if (newText.isEmpty()){
            if (category != null) {
                options =
                        new FirebaseRecyclerOptions.Builder<ProductDetails>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("details").equalTo(category), ProductDetails.class)
                                .build();
            }else{
                options =
                        new FirebaseRecyclerOptions.Builder<ProductDetails>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), ProductDetails.class)
                                .build();
            }
        }else {
            options =
                    new FirebaseRecyclerOptions.Builder<ProductDetails>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("title").startAt(newText).endAt(newText + "\uf8ff"), ProductDetails.class)
                            .build();

        }
        adapter = new productAdapter(options);
        adapter.startListening();
        recView.setAdapter(adapter);
    }
}
