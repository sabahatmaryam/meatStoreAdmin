package com.example.meatstoreadmin.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meatstoreadmin.Models.AdminOrders;
import com.example.meatstoreadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminNewOrdersActivity extends AppCompatActivity {

    RecyclerView order_List;
    DatabaseReference orderref,cartref;
      String phoneOfCurrentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);


        order_List=findViewById(R.id.order_List);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        order_List.setLayoutManager(mLayoutManager);


        orderref= FirebaseDatabase.getInstance().getReference().child("Orders");



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(orderref, AdminOrders.class)
                        .build();
        final FirebaseRecyclerAdapter<AdminOrders, AdminViewHold> adpater=new FirebaseRecyclerAdapter<AdminOrders, AdminViewHold> (options)
        {

            @NonNull
            @Override
            public AdminViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
                AdminViewHold holder = new AdminViewHold(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull AdminViewHold holder,final int position, @NonNull final AdminOrders model) {
                holder.address.setText("Address "+model.getAddress());
                holder.phone.setText("Phone "+model.getPhone());

                phoneOfCurrentOrder=model.getPhone().toString();

                holder.price.setText("Price "+model.getTotalAmount());
                holder.date.setText("Date  "+model.getDate());

                //admin can remove orders
                holder.btn_shipped.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[]=new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder1.setTitle("Have you shipped this order");
                        builder1.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //if this postion is 0 means admin pressed yes
                                if(i==0){
                                    String id=getRef(position).getKey();

                                    removeOrderByAdmin(id);
                                }
                                //if order has not been shipped yet
                                else
                                {
                                    finish();
                                }




                            }
                        });

                        builder1.show();
                    }
                });


                //When order is received by the user, Order will be finslly removed from AdminView -> CartList
                holder.btn_received.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[]=new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder1.setTitle("Customer Has received the order?");
                        builder1.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //if this postion is 0 means admin pressed yes
                                if(i==0){
                                    String id=getRef(position).getKey();
                                    removeOrderByAdminFromCartList(id);
                                }
                                //if order has not been shipped yet
                                else
                                {
                                    finish();
                                }




                            }
                        });

                        builder1.show();
                    }
                });


                //Admin can view the details of the order

                holder.btn_show_products.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getApplicationContext(),AdminUserProducts.class);

                        String id=getRef(position).getKey();

                        //sending phone number to next activity where we want to access products
                        i.putExtra("uid",id);
                        startActivity(i);
                    }
                });
            }
        };

        order_List.setAdapter(adpater);
        adpater.startListening();

    }

    private void removeOrderByAdminFromCartList(String id) {

        final DatabaseReference cartemref= FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("Admin View");
        cartemref.child(phoneOfCurrentOrder).removeValue();

        orderref.child(id).removeValue();

    }

    //when admin has shipped the order, he'll remove it
    private void removeOrderByAdmin(String id) {

        final HashMap<String,Object> ordersUpdateMap=new HashMap<>();



        ordersUpdateMap.put("state","shipped");

        orderref.child(phoneOfCurrentOrder).updateChildren(ordersUpdateMap);







    }

    public static class AdminViewHold extends RecyclerView.ViewHolder {
        public TextView txt_meat_name, txt_meat_price,meatQuantity,address, date,phone,price;
   public Button btn_show_products, btn_shipped,btn_received;

        public AdminViewHold(View itemView) {
            super(itemView);
            txt_meat_name = (TextView) itemView.findViewById(R.id.txt_meat_name);
            txt_meat_price = (TextView) itemView.findViewById(R.id.txt_meat_price);
            meatQuantity = (TextView) itemView.findViewById(R.id.meatQuantity);
            address=itemView.findViewById(R.id.address);
                    date=itemView.findViewById(R.id.date);
                            phone=itemView.findViewById(R.id.phone);
                            price=itemView.findViewById(R.id.meatPrice);
                                    btn_show_products=itemView.findViewById(R.id.btn_show_products);
            btn_shipped=itemView.findViewById(R.id.btn_shipped);
            btn_received=itemView.findViewById(R.id.btn_received);




        }
    }
}
