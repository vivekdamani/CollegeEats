package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collegeeats.adapter.ProductViewHolder2;
import com.example.collegeeats.adapter.ProductViewHolder3;
import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.Items;
import com.example.collegeeats.model.Orders;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PastOrderActivity extends AppCompatActivity {
    //TextView poname,poquantity,poprice;
    private RecyclerView recyclerView;
    private FirebaseFirestore rootRef;
    private FirestoreRecyclerAdapter<Orders, ProductViewHolder3> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order);

        recyclerView =findViewById(R.id.recitem2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("orders").whereEqualTo("ouid",Info.curuser.getUid()).orderBy("ostatus", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Orders> options = new FirestoreRecyclerOptions.Builder<Orders>()
                .setQuery(query, Orders.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Orders, ProductViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder3 productViewHolder, int position, @NonNull Orders productModel) {
                productViewHolder.setProductName(productModel.getOitem() ,productModel.getOtotal(),getApplicationContext(),productModel);

            }

            @NonNull
            @Override
            public ProductViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product2,parent, false);
                return new ProductViewHolder3(view);
            }
        };
        recyclerView.setAdapter(adapter);



    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }





}