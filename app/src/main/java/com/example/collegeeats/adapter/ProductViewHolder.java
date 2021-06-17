package com.example.collegeeats.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.collegeeats.EditFoodItemActivity;
import com.example.collegeeats.R;
import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.Items;
import com.example.collegeeats.model.Orders;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private View view;

    public ProductViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }




    public void setProductName(String productName, int productPrice, String productImage, Context mctx , Items item) {
        TextView textView1= view.findViewById(R.id.text_view1);
        textView1.setVisibility(View.VISIBLE);
        textView1.setText(productName);
        TextView textView2=view.findViewById(R.id.text_view2);
        textView2.setVisibility(View.VISIBLE);
        String s=Integer.toString(productPrice);
        textView2.setText(s);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);





        ImageView imageview=view.findViewById(R.id.image_view);
        imageview.setVisibility(View.VISIBLE);
        Glide.with(mctx).load(productImage).apply(options).into(imageview);
       // ImageView imgView=view.findViewById(R.id.image_view1);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mctx, EditFoodItemActivity.class);
                Info.curitem=new Items();
                Info.curitem=item;
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mctx.startActivity(i);

            }
        });

    }


}