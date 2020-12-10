package com.example.sportify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportify.tools.Category;
import com.example.sportify.tools.Product;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RecyclerViewerAdapter extends RecyclerView.Adapter<RecyclerViewerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category> mData;

    public RecyclerViewerAdapter() {
    }

    public RecyclerViewerAdapter(Context mContext,List<Category>mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categry_name;
        ImageView catgery_img;
        CardView cardView;

        public MyViewHolder(View view){
            super(view);
            categry_name = (TextView) view.findViewById(R.id.category);
            catgery_img = (ImageView) view.findViewById(R.id.categore_img_id);
            cardView =(CardView) view.findViewById(R.id.cardview_id);
            //set click listener

        }
    }
    @NonNull
    @Override
    public RecyclerViewerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflat = LayoutInflater.from(mContext);
        view = mInflat.inflate(R.layout.cardview_category,parent,false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewerAdapter.MyViewHolder holder, int position) {
            holder.categry_name.setText(mData.get(position).getName());
            holder.catgery_img.setImageResource(mData.get(position).getPosition());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,Product_activity.class);
                    intent.putExtra("name",mData.get(position).getName());
                    intent.putExtra("Category",mData.get(position).getCategory());
                    intent.putExtra("position",mData.get(position).getPosition());
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
