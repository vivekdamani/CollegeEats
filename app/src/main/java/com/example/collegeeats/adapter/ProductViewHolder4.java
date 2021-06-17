package com.example.collegeeats.adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
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
import com.example.collegeeats.PendingOrdersDetailsActivity;
import com.example.collegeeats.R;
import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.ItemQuantity;
import com.example.collegeeats.model.Orders;
import com.example.collegeeats.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductViewHolder4 extends RecyclerView.ViewHolder {
    private View view;


    public ProductViewHolder4(View itemView) {
        super(itemView);
        view = itemView;
    }




    public void setProductName(String id, Context mctx, Orders ord) {
        TextView textView1= view.findViewById(R.id.pending_order_id);
        textView1.setVisibility(View.VISIBLE);
        textView1.setText(id);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mctx, PendingOrdersDetailsActivity.class);
                Info.curorder=new Orders();
                Info.curorder=ord;
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mctx.startActivity(i);

            }
        });


        //Log.d("aeaeae",productName);
    }



    //public void setProductImage(String image) {
    //  ImageView imageView=view.findViewById(R.id.image_view);
    //imageView.setVisibility(View.VISIBLE);
    //}
}