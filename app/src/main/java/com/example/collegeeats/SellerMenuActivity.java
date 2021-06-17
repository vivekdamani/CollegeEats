package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collegeeats.adapter.ProductViewHolder;
import com.example.collegeeats.model.Items;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SellerMenuActivity extends AppCompatActivity {
    Button Additem;
   // ImageView img;


    FirebaseFirestore rootRef;
    RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Items, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_menu);
        Additem=(Button) findViewById(R.id.AddItem);



        recyclerView =findViewById(R.id.recitem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("items")
                .orderBy("price", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<Items> options = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query, Items.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Items, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position, @NonNull Items productModel) {
                productViewHolder.setProductName(productModel.getName(),productModel.getPrice(),productModel.getImage(),getApplicationContext(),productModel);




                Log.d("aaa",productModel.getName());
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new ProductViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        Additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SellerMenuActivity.this,AddItemActivity.class);
                startActivity(i);
            }
        });


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
