package com.shafiq.foodrecipe;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView mFoodImage;
    private TextView mFoodDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFoodImage = findViewById(R.id.ivCover);
        mFoodDescription = findViewById(R.id.txtDescription);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            mFoodImage.setImageResource(bundle.getInt("Image"));
            mFoodDescription.setText(bundle.getString("Description"));

            Glide.with(this)
                    .load(bundle.getString("Image"))
                    .into(mFoodImage);
        }
    }
}
