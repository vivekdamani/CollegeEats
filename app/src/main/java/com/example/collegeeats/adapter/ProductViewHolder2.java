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

public class ProductViewHolder2 extends RecyclerView.ViewHolder {
    private View view;

    public ProductViewHolder2(View itemView) {
        super(itemView);
        view = itemView;
    }




    public void setProductName(String id,String productName, int productPrice, String productImage, Context mctx) {
        TextView textView1= view.findViewById(R.id.text_view11);
        textView1.setVisibility(View.VISIBLE);
        textView1.setText(productName);
        TextView textView2=view.findViewById(R.id.text_view21);
        textView2.setVisibility(View.VISIBLE);
        String s=Integer.toString(productPrice);
        TextView quan=view.findViewById(R.id.quan);
        TextView minus=view.findViewById(R.id.minus);
        TextView addi=view.findViewById(R.id.addi);


        if(Info.curCart.containsKey(id))
        {
            quan.setText(String.valueOf(Info.curCart.get(id).getQuantity()));
        }
        textView2.setText(s);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);




        ImageView imageview=view.findViewById(R.id.image_view1);
        imageview.setVisibility(View.VISIBLE);
        Glide.with(mctx).load(productImage).apply(options).into(imageview);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q=Integer.parseInt(quan.getText().toString());

                if(q>0)
                {
                    q--;
                }
                Info.curCart.remove(id);
                if(q==0)
                {

                }
                else
                { ItemQuantity ii=new ItemQuantity(productName,productPrice,q);

                    Info.curCart.put(id,ii);
                }
                quan.setText(String.valueOf(q));            }
        });
        addi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q=Integer.parseInt(quan.getText().toString());

                q++;
                Info.curCart.remove(id);
                ItemQuantity ii=new ItemQuantity(productName,productPrice,q);

                Info.curCart.put(id,ii);
                quan.setText(String.valueOf(q));
            }
        });
        //Log.d("aeaeae",productName);
    }



    //public void setProductImage(String image) {
    //  ImageView imageView=view.findViewById(R.id.image_view);
    //imageView.setVisibility(View.VISIBLE);
    //}
}