package com.example.sportify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sportify.PlacedActivity;
import com.example.sportify.ui.CartItem;
import com.example.sportify.ProductDetails;
import com.example.sportify.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    FirebaseAuth ref = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    cartAdapter
            adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activity);

        mbase
                = FirebaseDatabase.getInstance().getReference().child("prod");


        recyclerView = findViewById(R.id.recyclerView1337);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<CartItem> options
                = new FirebaseRecyclerOptions.Builder<CartItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("customers").child(ref.getCurrentUser().getUid()).child("z_bought products"), CartItem.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new cartAdapter(options);
        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);
        bt = findViewById(R.id.placeorder);
        bt.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Processing your order", Toast.LENGTH_SHORT).show();
//        FirebaseDatabase.getInstance().getReference().child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").setValue("True");
            Intent placed = new Intent(getApplicationContext(), PlacedActivity.class);
            startActivity(placed);
        });
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}