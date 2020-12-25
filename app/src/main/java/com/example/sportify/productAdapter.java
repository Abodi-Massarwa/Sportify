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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class productAdapter extends FirebaseRecyclerAdapter<ProductDetails, productAdapter.product_view_holder> {

    static class product_view_holder extends RecyclerView.ViewHolder {
        FirebaseAuth ref=FirebaseAuth.getInstance();
        TextView title_text,Category, price_text, Rating, utc_text;
        Button buy_button;
        ImageView img;
        public product_view_holder(@NonNull View itemView)
        {
            // get values from single_products.xml
            super(itemView);
            title_text = itemView.findViewById(R.id.product_id);
            Category = itemView.findViewById(R.id.details_text);
            price_text = itemView.findViewById(R.id.price_text);
            Rating = itemView.findViewById(R.id.id_rating_text);
            img = itemView.findViewById(R.id.image_product_id);
            buy_button = itemView.findViewById(R.id.buy_button1);

            // buy button
            buy_button.setOnClickListener(v -> {
                ProductDetails productDetails = new ProductDetails(title_text.getText().toString().trim(), Category.getText().toString().trim(), Rating.getText().toString().trim(), price_text.getText().toString().trim(), utc_text.getText().toString().trim());
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("customers/"+ Objects.requireNonNull(ref.getCurrentUser()).getUid()+"/z_bought products/"+productDetails.getTitle());
                ref1.child(ref.getCurrentUser().getUid()).setValue(productDetails);
            });
        }
    }

    public productAdapter(@NonNull FirebaseRecyclerOptions<ProductDetails> options) { super(options);}

    @Override
    protected void onBindViewHolder(@NonNull product_view_holder holder, int position, @NonNull ProductDetails product)
    {
       holder.Category.setText("Details: " + product.getDetails());
       holder.price_text.setText("Price: " + product.getPrice());
      // holder.utc_text.setText("Utc: " + product.getUtc());
       holder.Rating.setText("Condition: " + product.getCondition());
       holder.title_text.setText("Product name: " + product.getTitle());
       Glide.with(holder.img.getContext()).load(product.getImage_url()).into(holder.img);
    }

    @NonNull
    @Override
    public product_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
       return new product_view_holder(view);
    }



}
