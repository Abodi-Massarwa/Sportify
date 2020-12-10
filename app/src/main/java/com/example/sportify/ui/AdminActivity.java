package com.example.sportify.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportify.ProductDetails;
import com.example.sportify.R;
import com.example.sportify.tools.Product;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;

public class AdminActivity extends AppCompatActivity {
    EditText name;
    EditText price;
    EditText details;
    EditText url;
    EditText condition;
    /*
     */
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference dbRef=db.getReference("Products");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

         name=findViewById(R.id.product_name);
         price=findViewById(R.id.product_price);
         details=findViewById(R.id.product_details);
         url=findViewById(R.id.product_url);
         condition=findViewById(R.id.product_condition);
    }


    /*
    click listener for product adding in the admin's section
     */

    public void add_product(View view) {
        String id=dbRef.push().getKey();
        ProductDetails pd= new ProductDetails(name.getText().toString().trim(),details.getText().toString().trim(), condition.getText().toString().trim(), price.getText().toString().trim(),id);
        pd.setImage_url(url.getText().toString());

        dbRef.child(id).setValue(pd, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error==null)
                Toast.makeText(AdminActivity.this, "Successfully added product", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AdminActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
            }
        });

    }
}