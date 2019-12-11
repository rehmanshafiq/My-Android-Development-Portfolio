package com.shafiq.foodrecipe.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_item,
                viewGroup, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {

        foodViewHolder.mRecipeImageView.setImageResource(mFoodList.get(i).getFoodImage());
        foodViewHolder.mTitle.setText(mFoodList.get(i).getName());
        foodViewHolder.mDescription.setText(mFoodList.get(i).getDescription());
        foodViewHolder.mPrice.setText(mFoodList.get(i).getPrice());
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

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        mRecipeImageView = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mDescription = itemView.findViewById(R.id.tvDescription);
        mCardView = itemView.findViewById(R.id.cardView);
    }
}
