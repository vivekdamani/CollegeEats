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

import com.example.collegeeats.adapter.ProductViewHolder;
import com.example.collegeeats.adapter.ProductViewHolder2;
import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.Items;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeActivity extends AppCompatActivity {
    private FirebaseFirestore rootRef;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Items, ProductViewHolder2> adapter;
    Button Mycart;
    Button Myprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView =findViewById(R.id.recitem1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Mycart=findViewById(R.id.mycart);
        Myprofile=findViewById(R.id.myprofile);
        Myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });

        Mycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,CartActivity.class);
                startActivity(i);

            }
        });

//        Log.d("aaaa", Info.curuser.getName());
        rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("items")
                .orderBy("price", Query.Direction.ASCENDING).whereEqualTo("avail",true);


        FirestoreRecyclerOptions<Items> options = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query, Items.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Items, ProductViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder2 productViewHolder, int position, @NonNull Items productModel) {
                productViewHolder.setProductName(productModel.getId(),productModel.getName(),productModel.getPrice(),productModel.getImage(),getApplicationContext());

            }

            @NonNull
            @Override
            public ProductViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product1,parent, false);
                return new ProductViewHolder2(view);
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



       // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // if(user != null) {
            // Name, email address, and profile photo Url
        //    String name = user.getDisplayName();
         //   String email = user.getEmail();
           // Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
       //     boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
         //   String uid = user.getUid();
        //}
    //}
}