package com.example.sportify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class productAdapter extends FirebaseRecyclerAdapter<ProductDetails, productAdapter.product_view_holder> {

    static class product_view_holder extends RecyclerView.ViewHolder {
        FirebaseAuth ref = FirebaseAuth.getInstance();
        TextView title_text, details_text, price_text, condition_text, utc_text;
        Button buy_button;
        ImageView img;

        public product_view_holder(@NonNull View itemView) {
            // get values from single_products.xml
            super(itemView);
            title_text = itemView.findViewById(R.id.product_name_id);
            details_text = itemView.findViewById(R.id.category_name_id);
            price_text = itemView.findViewById(R.id.price_value_id);
            condition_text = itemView.findViewById(R.id.rating_text_id);
         //   utc_text = itemView.findViewById(R.id.utc_text);
            img = itemView.findViewById(R.id.image_product_id);
            buy_button = itemView.findViewById(R.id.buy_button1);

            // buy button
            buy_button.setOnClickListener(v -> {
                ProductDetails productDetails = new ProductDetails(title_text.getText().toString().trim(), details_text.getText().toString().trim(), condition_text.getText().toString().trim(), price_text.getText().toString().trim());
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("customers/" + Objects.requireNonNull(ref.getCurrentUser()).getUid() + "/z_bought products/" + productDetails.getTitle());
                ref1.child(ref.getCurrentUser().getUid()).setValue(productDetails);
                Snackbar.make(buy_button,"Order submitted",Snackbar.LENGTH_LONG).show();
            });
        }
    }

    public productAdapter(@NonNull FirebaseRecyclerOptions<ProductDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull product_view_holder holder, int position, @NonNull ProductDetails product) {
        holder.details_text.setText("Category: "  + product.getDetails());
        holder.price_text.setText("Price: " + product.getPrice());
        //holder.utc_text.setText("Utc: " + product.getUtc());
        holder.condition_text.setText("Rating: " + product.getCondition());
        holder.title_text.setText("Product name: " + product.getTitle());
        Glide.with(holder.img.getContext()).load(product.getImage_url()).into(holder.img);
    }

    @NonNull
    @Override
    public product_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items, parent, false);
        return new product_view_holder(view);
    }

}

