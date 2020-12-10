package com.example.sportify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class productsActivity extends AppCompatActivity {
    RecyclerView recView;
    DatabaseReference databaseReference;
    productAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        recView = findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ProductDetails> options =
                new FirebaseRecyclerOptions.Builder<ProductDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), ProductDetails.class)
                        .build();

        adapter = new productAdapter(options);
        recView.setAdapter(adapter);

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

}
