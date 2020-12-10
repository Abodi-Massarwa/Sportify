package com.example.sportify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Product_activity extends AppCompatActivity {
    private TextView category,Des,nm;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_activity);
        category = (TextView) findViewById(R.id.category_id);
        Des = (TextView)findViewById(R.id.details_id);
        nm = (TextView) findViewById(R.id.product_name);
        img = (ImageView)findViewById(R.id.product_id2);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("name");
        String Category = intent.getExtras().getString("Category");
        int image =  intent.getExtras().getInt("position");
        category.setText(Category);
        Des.setText(title);
        nm.setText(title);
        img.setImageResource(image);

    }
}