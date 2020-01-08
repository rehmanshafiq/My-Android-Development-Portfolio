package com.shafiq.foodrecipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shafiq.foodrecipe.adapters.FoodAdapter;
import com.shafiq.foodrecipe.model.Food;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // widgets and model
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;
    private FoodAdapter mFoodAdapter;
    private ArrayList<Food> mFoodList;
    private Food mFood;
    private GridLayoutManager gridLayoutManager;

    // firebase
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items....");

        mFoodList = new ArrayList<>();

        mFoodAdapter = new FoodAdapter(MainActivity.this, mFoodList);
        mRecyclerView.setAdapter(mFoodAdapter);

        retrieveValueFromFirebase();

    }

    private void retrieveValueFromFirebase() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe");
        progressDialog.show();
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFoodList.clear();

                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    Food food = itemSnapshot.getValue(Food.class);
                    mFoodList.add(food);
                }
                mFoodAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    public void btnUploadActivity(View view) {
        startActivity(new Intent(this, UploadRecipeActivity.class));
    }
}
