package com.example.meatstoreadmin.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meatstoreadmin.MainActivity;
import com.example.meatstoreadmin.R;

public class AdminDashboard extends AppCompatActivity {
    private ImageView chicken, fish, lamb, fruits;
    private ImageView beef, organic_chik, veggies ;
    Button btn_chk_orders,btn_admin_log_out,mantain_products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        btn_chk_orders=findViewById(R.id.btn_chk_orders);
        btn_admin_log_out=findViewById(R.id.btn_admin_log_out);
        mantain_products=findViewById(R.id.mantain_products);

        mantain_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AdminMantainProduct.class));
            }
        });


        //admin logout
        btn_admin_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });

        btn_chk_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AdminNewOrdersActivity.class);

                startActivity(i);


            }
        });
        chicken = (ImageView) findViewById(R.id.chicken);
        fish = (ImageView) findViewById(R.id.fish);
        lamb = (ImageView) findViewById(R.id.lamb);
        fruits = (ImageView) findViewById(R.id.fruits);

        beef = (ImageView) findViewById(R.id.beef);
        organic_chik = (ImageView) findViewById(R.id.veggies);
        veggies  = (ImageView) findViewById(R.id.organic_chik);

chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewProductAcitivity.class);
                intent.putExtra("category", "chicken");
                startActivity(intent);
            }
        });


        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewProductAcitivity.class);
                intent.putExtra("category", "fish");
                startActivity(intent);
            }
        });


        lamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewProductAcitivity.class);
                intent.putExtra("category", "lamb");
                startActivity(intent);
            }
        });


        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewProductAcitivity.class);
                intent.putExtra("category", "fruits");
                startActivity(intent);
            }
        });


        beef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewProductAcitivity.class);
                intent.putExtra("category", "beef");
                startActivity(intent);
            }
        });


        organic_chik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewProductAcitivity.class);
                intent.putExtra("category", "organic_chik");
                startActivity(intent);
            }
        });



        veggies .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewProductAcitivity.class);
                intent.putExtra("category", "veggies");
                startActivity(intent);
            }
        });



    }
}
