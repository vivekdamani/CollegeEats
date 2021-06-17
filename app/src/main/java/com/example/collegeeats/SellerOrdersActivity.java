package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collegeeats.adapter.ProductViewHolder2;
import com.example.collegeeats.adapter.ProductViewHolder4;
import com.example.collegeeats.model.Items;
import com.example.collegeeats.model.Orders;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SellerOrdersActivity extends AppCompatActivity {
    private FirebaseFirestore rootRef;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Orders, ProductViewHolder4> adapter;
   // TextView pending_o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_orders);
        //pending_o=findViewById(R.id.pending_order_id);
        recyclerView = findViewById(R.id.recitem3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        pending_o.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(SellerOrdersActivity.this,PendingOrdersDetailsActivity.class);
//                startActivity(i);
//
//            }
//        });
        rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("orders").orderBy("ostatus").whereLessThan("ostatus","3");
        FirestoreRecyclerOptions<Orders> options = new FirestoreRecyclerOptions.Builder<Orders>()
                .setQuery(query, Orders.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Orders, ProductViewHolder4>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder4 productViewHolder, int position, @NonNull Orders productModel) {
                productViewHolder.setProductName(productModel.getOid(), getApplicationContext(),productModel);

            }
            @NonNull
            @Override
            public ProductViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product3,parent, false);
                return new ProductViewHolder4(view);
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