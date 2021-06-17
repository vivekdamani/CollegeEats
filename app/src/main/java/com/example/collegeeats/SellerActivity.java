package com.example.collegeeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellerActivity extends AppCompatActivity {
    Button MyMenu;
    Button P_Orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        MyMenu=(Button) findViewById(R.id.Mymenu);
        P_Orders=(Button) findViewById(R.id.Porders);
        MyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SellerActivity.this,SellerMenuActivity.class);
                startActivity(i);

            }
        });
        P_Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SellerActivity.this,SellerOrdersActivity.class);
                startActivity(i);
            }
        });
    }
}