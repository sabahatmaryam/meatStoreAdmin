package com.example.meatstoreadmin;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView meatName,   meatPrice,meatQuantity,pid;

    public ItemClickListner listner;


    public CartViewHolder(View itemView)
    {
        super(itemView);



        meatName = (TextView) itemView.findViewById(R.id.txt_meat_name);
        meatPrice = (TextView) itemView.findViewById(R.id.txt_meat_price);
        meatQuantity = (TextView) itemView.findViewById(R.id.meatQuantity);
        pid=itemView.findViewById(R.id.pid);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }


}
