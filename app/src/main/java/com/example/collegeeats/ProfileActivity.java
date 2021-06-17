package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeeats.model.Info;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    EditText myname,myemail,mymobile,myaddress;

    Button updateInfo,pastOrd;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Updating Profile Details..");


        myname=findViewById(R.id.myname);
        myemail=findViewById(R.id.myemail);
        mymobile=findViewById(R.id.mymobile);
        myaddress=findViewById(R.id.myaddress);
        updateInfo=findViewById(R.id.updateinfo);
        pastOrd=findViewById(R.id.pastorders);

        myname.setText(Info.curuser.getName());
        myaddress.setText(Info.curuser.getAddress());
        myemail.setText(Info.curuser.getEmail());
        mymobile.setText(Info.curuser.getMobile());

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference dr=db.collection("user").document(Info.curuser.getUid());

                Info.curuser.setAddress(myaddress.getText().toString());
                Info.curuser.setName(myname.getText().toString());
                Info.curuser.setMobile(mymobile.getText().toString());
                Info.curuser.setEmail(myemail.getText().toString());
                dr.set(Info.curuser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();


                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this,"Update Failed",Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        pastOrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ProfileActivity.this,PastOrderActivity.class);
                startActivity(i);

            }
        });




    }
}