package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.Orders;
import com.example.collegeeats.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingOrdersDetailsActivity extends AppCompatActivity {
    TextView orderItem,orderStatus,orderTotal,ouseraddress,ouseremail,ousername,ousermobile;
    private FirebaseFirestore db;
    Button b,b1;
    User user=new User();
    ProgressDialog progressDialog;

    int os;
    User oUser=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders_details);
        b=findViewById(R.id.changeStatus);
        b1=findViewById(R.id.cancelOrder);
        orderItem=findViewById(R.id.order_details_item);
        orderStatus=findViewById(R.id.order_details_status);
        orderTotal=findViewById(R.id.order_details_total);
        ouseraddress=findViewById(R.id.user_details_address);
        ouseremail=findViewById(R.id.user_details_email);
        ousermobile=findViewById(R.id.user_details_mobile);
        ousername=findViewById(R.id.user_details_name);
        orderItem.setText(Info.curorder.getOitem());
        os=Integer.parseInt(Info.curorder.getOstatus());
        orderStatus.setText(Info.curorder.getOstatus());
        orderTotal.setText(Info.curorder.getOtotal());
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Updating Status..");
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(Info.curorder.getOuid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                oUser=new User();
                oUser=documentSnapshot.toObject(User.class);
                ousername.setText(oUser.getName());
                ouseremail.setText(oUser.getEmail());
                ouseraddress.setText(oUser.getAddress());
                ousermobile.setText(oUser.getMobile());
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                os=4;
                Info.curorder.setOstatus(String.valueOf(os));
                DocumentReference docRef = db.collection("orders").document(Info.curorder.getOid());
                Map<String,Object> m=new HashMap<>();
                m.put("ostatus", Info.curorder.getOstatus());
                docRef.update(m);

                docRef=db.collection("user").document(Info.curorder.getOuid());
                DocumentReference finalDocRef = docRef;
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                      @Override
                                                      public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                          progressDialog.dismiss();


                                                          user=documentSnapshot.toObject(User.class);

                                                          List<Orders> l=new ArrayList<>();
                                                          l=user.getPastorders();
                                                          for(int i=0;i<l.size();i++)
                                                          {
                                                              if(l.get(i).getOid().equals(Info.curorder.getOid()))
                                                              {
                                                                  l.set(i,Info.curorder);
                                                                  break;
                                                              }
                                                          }
                                                          user.setPastorders(l);
                                                          Map<String,Object> mm=new HashMap<>();
                                                          mm.put("pastorders",l);
                                                          finalDocRef.update(mm);

                                                          Toast.makeText(PendingOrdersDetailsActivity.this,"Status Updated",Toast.LENGTH_SHORT).show();
                                                          finish();
                                                      }
                                                  }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(PendingOrdersDetailsActivity.this,"Status Update Failed",Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


        String s="";
        if(os==0)
        {
            s="Accept Order ? ";
        }
        else if(os==1)
        {
            s="Is it Out for delivery?";
        }else if(os==2)
        {
            s="Is it Delivered?";
        }
        b.setText(s);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                os++;
                Info.curorder.setOstatus(String.valueOf(os));
                DocumentReference docRef = db.collection("orders").document(Info.curorder.getOid());
                Map<String,Object> m=new HashMap<>();
                m.put("ostatus", Info.curorder.getOstatus());
                docRef.update(m);
                docRef=db.collection("user").document(Info.curorder.getOuid());
                DocumentReference finalDocRef = docRef;
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                      @Override
                                                      public void onSuccess(DocumentSnapshot documentSnapshot) {


                                                          user=documentSnapshot.toObject(User.class);

                                                          List<Orders> l=new ArrayList<>();
                                                          l=user.getPastorders();
                                                          for(int i=0;i<l.size();i++)
                                                          {
                                                              if(l.get(i).getOid().equals(Info.curorder.getOid()))
                                                              {
                                                                  l.set(i,Info.curorder);
                                                                  break;
                                                              }
                                                          }
                                                          user.setPastorders(l);
                                                          Map<String,Object> mm=new HashMap<>();
                                                          mm.put("pastorders",l);
                                                          finalDocRef.update(mm);

                                                      }
                                                  }
                );

            }
        });




        if(os==4)
        {
            b1.setVisibility(View.INVISIBLE);
            b.setEnabled(false);
            b1.setEnabled(false);
            b.setText("Order Cancelled");

        }
        if(os==3)
        {
            b1.setVisibility(View.INVISIBLE);
            b.setEnabled(false);
            b1.setEnabled(false);
            b.setText("Order Delivered");
        }





    }
}