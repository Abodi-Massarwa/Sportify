package com.example.sportify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class
PlacedActivity extends AppCompatActivity{
    TextView total_order_price;
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference dbr;
    FirebaseAuth auth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed);
        dbr=db.getReference("customers/"+ Objects.requireNonNull(auth.getCurrentUser()).getUid()+"/z_bought products/");
        /*
       TODO: lets traverse the tree of products calculate their price and add it to the total price as a value of child in this path
         */
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double total_price=0;
                String order_ref_id=db.getReference("orders/").child(auth.getCurrentUser().getUid()).push().getKey();
                DatabaseReference order_ref=db.getReference("orders/").child(auth.getCurrentUser().getUid()).child(order_ref_id);
                for(DataSnapshot child: snapshot.getChildren())
                {
                    if(child.hasChildren()) {
                        total_price += Double.parseDouble(child.child("total").getValue().toString());
                        String id=order_ref.push().getKey();
                        order_ref.child(id).setValue(child.getValue());
                    }
                }
                dbr.removeValue();
                order_ref.child("total_order_price").setValue(total_price);
                total_order_price= findViewById(R.id.total_price);
                total_order_price.setText("Total Price: "+total_price+"₪");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}