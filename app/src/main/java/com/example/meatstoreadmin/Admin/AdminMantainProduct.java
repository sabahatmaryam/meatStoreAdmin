package com.example.meatstoreadmin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meatstoreadmin.Models.Meat;
import com.example.meatstoreadmin.ProductDetailsActivity;
import com.example.meatstoreadmin.ProductViewHolder;
import com.example.meatstoreadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class AdminMantainProduct extends AppCompatActivity {
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mantain_product);

        recyclerView =  findViewById(R.id.recycler_view_for_customer_products_display);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Paper.init(getApplicationContext());

        //Display ALL ITEMS

        Query query=FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Meat> options =
                new FirebaseRecyclerOptions.Builder<Meat>()
                        .setQuery(query, Meat.class)
                        .build();


        FirebaseRecyclerAdapter<Meat, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Meat, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Meat model) {
                        holder.meatName.setText(model.getMeatName());

                        holder.meatPrice.setText("Rs.  " + model.getMeatPrice()+" /KG");
                        Picasso.get().load(model.getMeatImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent i = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                                i.putExtra("pid", model.getPid());
                                startActivity(i);


                            }
                        });


                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_items_for_sale, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
