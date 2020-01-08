package com.shafiq.foodrecipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shafiq.foodrecipe.DetailActivity;
import com.shafiq.foodrecipe.R;
import com.shafiq.foodrecipe.model.Food;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder>{

    private Context mContext;
    private ArrayList<Food> mFoodList;

    public FoodAdapter(Context mContext, ArrayList<Food> mFoodList) {
        this.mContext = mContext;
        this.mFoodList = mFoodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_item,
                viewGroup, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder foodViewHolder, int i) {

        Glide.with(mContext)
                .load(mFoodList.get(i).getFoodImage())
                .into(foodViewHolder.mRecipeImageView);
        //foodViewHolder.mRecipeImageView.setImageResource(mFoodList.get(i).getFoodImage());
        foodViewHolder.mTitle.setText(mFoodList.get(i).getName());
        foodViewHolder.mDescription.setText(mFoodList.get(i).getDescription());
        foodViewHolder.mPrice.setText(mFoodList.get(i).getPrice());

        foodViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("Image", mFoodList.get(foodViewHolder.getAdapterPosition()).getFoodImage());
                intent.putExtra("Description", mFoodList.get(foodViewHolder.getAdapterPosition()).getDescription());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }
}

class FoodViewHolder extends RecyclerView.ViewHolder {

    ImageView mRecipeImageView;
    TextView mTitle, mDescription, mPrice;
    CardView mCardView;

    public FoodViewHolder(View itemView) {
        super(itemView);

        mRecipeImageView = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mDescription = itemView.findViewById(R.id.tvDescription);
        mPrice = itemView.findViewById(R.id.tvPrice);
        mCardView = itemView.findViewById(R.id.cardView);
    }
}
