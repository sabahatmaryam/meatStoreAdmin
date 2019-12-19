package com.example.meatstoreadmin.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.meatstoreadmin.CartViewHolder;
import com.example.meatstoreadmin.Models.CartModel;
import com.example.meatstoreadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProducts extends AppCompatActivity {
    RecyclerView rv_products_for_Admin;
    DatabaseReference cartRef;
      String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        rv_products_for_Admin=findViewById(R.id.rv_products_for_Admin);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_products_for_Admin.setLayoutManager(mLayoutManager);

         //getting phone no from previous activity
        uid=getIntent().getStringExtra("uid");


        //first refer to carlist node

        cartRef= FirebaseDatabase.getInstance().getReference().child("Cart List")
        .child("Admin View").child(uid)
        .child("Products");


        ;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(cartRef, CartModel.class)
                        .build();
        final FirebaseRecyclerAdapter<CartModel, CartViewHolder> adpater=new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {


            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int i, @NonNull CartModel cartModel) {
                holder.meatName.setText("Product Name"+cartModel.getMeatName());
                holder.meatPrice.setText("Price"+cartModel.getMeatPrice());
                holder.meatQuantity.setText("Quantity"+cartModel.getMeatQuantity());
            }
        };
        rv_products_for_Admin.setAdapter(adpater);
        adpater.startListening();
    }
}
