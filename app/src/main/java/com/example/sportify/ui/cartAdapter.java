package com.example.sportify.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportify.R;
import com.example.sportify.tools.CartItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class cartAdapter extends FirebaseRecyclerAdapter<CartItem, cartAdapter.CartItemHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public cartAdapter(@NonNull FirebaseRecyclerOptions<CartItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CartItemHolder holder, int position, @NonNull CartItem model) {
        holder.p_name.setText(model.getTitle());
        holder.p_price.setText(model.getPrice());
        holder.p_quantity.setText(model.getQuantity());
        Glide.with(holder.p_image.getContext()).load(model.getImage_url()).into(holder.p_image);


    }

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cart_item, parent, false);
        return new CartItemHolder(view);
    }


    class CartItemHolder extends RecyclerView.ViewHolder {
        TextView p_name,p_price,p_quantity;
        ImageView p_image;
        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            this.p_image=itemView.findViewById(R.id.cart_item_image);
            this.p_name=itemView.findViewById(R.id.cart_item_title);
//            this.p_details=itemView.findViewById(R.id.product_details);
            this.p_price=itemView.findViewById(R.id.cart_item_price);
            this.p_quantity=itemView.findViewById(R.id.cart_item_quantity);


        }
    }
}