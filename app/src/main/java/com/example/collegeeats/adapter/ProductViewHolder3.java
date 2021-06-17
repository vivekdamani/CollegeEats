package com.example.collegeeats.adapter;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.collegeeats.R;
import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.ItemQuantity;
import com.example.collegeeats.model.Orders;

public class ProductViewHolder3 extends RecyclerView.ViewHolder {
    private View view;

    public ProductViewHolder3(View itemView) {
        super(itemView);
        view = itemView;
    }




    public void setProductName(String poitem, String pototal , Context mctx, Orders ord) {
        TextView textView1 = view.findViewById(R.id.po_item);
        textView1.setVisibility(View.VISIBLE);
        textView1.setText(poitem);
        TextView textView2 = view.findViewById(R.id.po_total);
        textView2.setVisibility(View.VISIBLE);
        textView2.setText(pototal);
        TextView textView3=view.findViewById(R.id.status_value);
        textView3.setVisibility(View.VISIBLE);
        String s=ord.getOstatus();
        if(s.equals("1"))
        {
            textView3.setText("Order Accepted");
        }
        else if(s.equals("2"))
        {
            textView3.setText("Order Out for delievry");
        }
        else if(s.equals("3"))
        {
            textView3.setText("Order Deleiverd");
        }
        else if(s.equals("4"))
        {
            textView3.setText("Order Canceled");
        }
        else{
            textView3.setText("Order not accepted yet");
        }



    }
}