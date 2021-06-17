package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.ItemQuantity;
import com.example.collegeeats.model.Orders;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {

    Button confirmOrder;
    TextView myorder,orderTotal;
    int t=0;
    int total=0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        confirmOrder=findViewById(R.id.confirmOrder);
        myorder=findViewById(R.id.myorder);
        orderTotal=findViewById(R.id.orderTotal);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Placing Order...");

        total=0;

        t=0;

        Iterator hmIterator = Info.curCart.entrySet().iterator();



        while (hmIterator.hasNext()) {
            Map.Entry<String,ItemQuantity> mapElement = (Map.Entry)hmIterator.next();

            ItemQuantity i=new ItemQuantity();
            i=mapElement.getValue();

            String s=myorder.getText().toString();
            if(t==0){
                s="Name       Quantity      Price. \n";
                t++;
            }
            s=s+i.getName()+"            "+i.getQuantity()+"              "+i.getPrice()+". \n";
            total += i.getQuantity()*i.getPrice();
            myorder.setText(s);





            Log.d("jj",i.getName().toString());

            //int marks = ((int)mapElement.getValue() + 10);
            //ystem.out.println(mapElement.getKey() + " : " + marks);
        }


        String u=orderTotal.getText().toString()+String.valueOf(total);
        orderTotal.setText(u);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t==0)
                {
                    Toast.makeText(CartActivity.this,"Empty cart",Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    Orders o = new Orders();

                    o.setOid(UUID.randomUUID().toString());
                    o.setOitem(myorder.getText().toString());
                    o.setOstatus("0");
                    o.setOuid(Info.curuser.getUid());
                    o.setOtotal(String.valueOf(total));

                    List<Orders> oo=new ArrayList<>();

                    oo=Info.curuser.getPastorders();
                    oo.add(o);
                    Info.curuser.setPastorders(oo);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("orders").document(o.getOid()).set(o);
                    DocumentReference dr=db.collection("user").document(Info.curuser.getUid());
                    dr.set(Info.curuser).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CartActivity.this,"Order Place Successfully",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(CartActivity.this,PastOrderActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CartActivity.this,"Unable to Place Order Now!!",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });


    }


}