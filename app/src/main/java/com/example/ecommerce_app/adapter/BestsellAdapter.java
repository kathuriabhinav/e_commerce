package com.example.ecommerce_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce_app.DetailActivity;
import com.example.ecommerce_app.R;
import com.example.ecommerce_app.domain.Bestsell;

import java.util.List;

public class BestsellAdapter extends RecyclerView.Adapter<BestsellAdapter.ViewHolder> {
    Context context;
    List<Bestsell> mBestsellList;
    public BestsellAdapter(Context context, List<Bestsell> mBestsellList) {
        this.context=context;
        this.mBestsellList=mBestsellList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_bestsell_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bestsell_name.setText(mBestsellList.get(position).getName());
        holder.bestsell_price.setText("₹ "+mBestsellList.get(position).getPrice());
        Glide.with(context).load(mBestsellList.get(position).getImg_url()).into(holder.bestsell_image);
        holder.bestsell_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name", mBestsellList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBestsellList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bestsell_image;
        TextView bestsell_name;
        TextView bestsell_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bestsell_name = itemView.findViewById(R.id.bestsell_name);
            bestsell_image = itemView.findViewById(R.id.bestsell_image);
            bestsell_price = itemView.findViewById(R.id.bestsell_price);
        }
    }
}
