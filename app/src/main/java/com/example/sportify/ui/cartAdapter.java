package com.example.sportify.ui;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportify.R;
import com.example.sportify.ui.CartItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportify.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;


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
            holder.p_total.setText("Total :" + model.getTotal());
            Glide.with(holder.p_image.getContext()).load(model.getImage_url()).into(holder.p_image);

//        holder.b_increase_quantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.dbr.child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("z_bought products").child(model.getTitle())
//            }
//        });
//        holder.b_reduce_quantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
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
            TextView p_name, p_price, p_quantity, p_total;
            ImageView p_image;
            private DecimalFormat df2 = new DecimalFormat("#.##");
            FloatingActionButton b_increase_quantity;
            FloatingActionButton b_reduce_quantity;
            Button b_remove;
            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference();

            public CartItemHolder(@NonNull View itemView) {
                super(itemView);
                this.p_image = itemView.findViewById(R.id.cart_item_image);
                this.p_name = itemView.findViewById(R.id.cart_item_title);
//            this.p_details=itemView.findViewById(R.id.product_details);
                this.p_price = itemView.findViewById(R.id.cart_item_price);
                this.p_quantity = itemView.findViewById(R.id.cart_item_quantity);
                this.p_total = itemView.findViewById(R.id.cart_item_total_price);
              /*
         BUTTONS
           */
                this.b_increase_quantity = itemView.findViewById(R.id.cart_item_increase);
                this.b_reduce_quantity = itemView.findViewById(R.id.cart_item_reduce);
                this.b_remove = itemView.findViewById(R.id.cart_item_remove);

            /*
            here we implement the on clicklisterners
             */
                b_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Removing item", Toast.LENGTH_LONG).show();
                        AlertDialog a_builder = new AlertDialog.Builder(v.getContext()).setTitle("Warning")
                                .setMessage("Are you sure you want to remove the desired product from you cart ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(v.getContext(), p_name.getText().toString().trim(), Toast.LENGTH_LONG).show();
                                        FirebaseDatabase.getInstance().getReference().child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("z_bought products")
                                                .child(p_name.getText().toString().trim())
                                                .setValue(null);
                                    }
                                }).setNegativeButton("Cancel removal", null).show();
                    }
                });
                b_increase_quantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("z_bought products")
                                .child(p_name.getText().toString().trim())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String current_quantity = (String) snapshot.child("quantity").getValue();
                                        Integer quantity = new Integer(current_quantity);
//                            Toast.makeText(v.getContext(),current_quantity,Toast.LENGTH_SHORT).show();
                                        quantity = quantity + 1;
                                        FirebaseDatabase.getInstance().getReference().child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("z_bought products")
                                                .child(p_name.getText().toString().trim())
                                                .child("quantity").setValue(quantity.toString());

                            /*
                            TODO: update total price
                             */
                                        String current_total = (String) snapshot.child("total").getValue();
                                        Double currentTotal = Double.parseDouble(current_total);
                                        Log.d("TAGG", currentTotal.toString());
                                        String current_price = (String) snapshot.child("price").getValue();
                                        Log.d("TAGG", current_price.toString());
                                        Double currentPrice = Double.parseDouble(current_price.split(":")[1].trim());
                                        currentTotal = currentTotal + currentPrice;
                                        dbr.child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("z_bought products")
                                                .child(p_name.getText().toString().trim())
                                                .child("total").setValue(df2.format(currentTotal).toString());
                                        Log.d("TAGG", current_total.toString());
//                            Integer current_price= new Integer(((String)snapshot.child("price").getValue()).split(":")[1].trim().split("₪")[0]);
//                            int total_price=quantity*current_price;
//                             /*
//                            TODO : update the total price ++
//                             */
//                            TextView tv_total_price=itemView.findViewById(R.id.total_price);
//                            tv_total_price.setText(total_price+"");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                    }
                });

                b_reduce_quantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("z_bought products")
                                .child(p_name.getText().toString().trim())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String current_quantity = (String) snapshot.child("quantity").getValue();
                                        Integer quantity = new Integer(current_quantity);
//                            Toast.makeText(v.getContext(),current_quantity,Toast.LENGTH_SHORT).show();
                                        quantity--;
                                        FirebaseDatabase.getInstance().getReference().child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("z_bought products")
                                                .child(p_name.getText().toString().trim())
                                                .child("quantity").setValue(quantity.toString());
                                /*
                            TODO : update the total price --
                             */
                            /*
                            TODO: update total price
                             */
                                        String current_total = (String) snapshot.child("total").getValue();
                                        Log.d("TAGG", snapshot.child("total").getValue().toString());
                                        Double currentTotal = Double.parseDouble(current_total);
                                        String current_price = (String) snapshot.child("price").getValue();
                                        Log.d("TAGG", current_price.toString());
                                        Double currentPrice = Double.parseDouble(current_price.split(":")[1].trim());
                                        currentTotal = currentTotal - currentPrice;
                                        dbr.child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("z_bought products")
                                                .child(p_name.getText().toString().trim())
                                                .child("total").setValue(df2.format(currentTotal).toString());
                                        Log.d("TAGG", current_total.toString());


//                            Integer current_price= new Integer(((String)snapshot.child("price").getValue()).split(":")[1].trim().split("₪")[0]);
//                            int total_price=quantity*current_price;
//
//                            TextView tv_total_price=itemView.findViewById(R.id.total_price);
//                            tv_total_price.setText(total_price+"");

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }
                });

            }
        }
    }
