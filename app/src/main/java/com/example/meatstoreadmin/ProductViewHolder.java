package com.example.meatstoreadmin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView meatName,   meatPrice, pid;
    public ImageView imageView;
    public ItemClickListner listner;


    public ProductViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.iv_meat);
        meatName = (TextView) itemView.findViewById(R.id.txt_meat_name);
        meatPrice = (TextView) itemView.findViewById(R.id.txt_meat_price);

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