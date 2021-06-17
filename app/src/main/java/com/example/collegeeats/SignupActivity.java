package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.Orders;
import com.example.collegeeats.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private String TAG="debugSA";
    EditText etemail,etpassword,etaddress,etmobile,etname;
    String email,password,mobile,address,name;
    Button cSignup;
    private  String uid;
    private List<Orders> initialOrders;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        etemail=findViewById(R.id.signup_id);
        initialOrders=new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Creating account...");
//        initialOrders=new List<Orders>() {
//            @Override
//            public int size() {
//                return 0;
//            }
//        };

        etpassword=findViewById(R.id.signup_password);
        etaddress=findViewById(R.id.signup_address);
        etname=findViewById(R.id.signup_name);
        etmobile=findViewById(R.id.signup_mno);


        cSignup=findViewById(R.id.signup_confirm);

        cSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=etemail.getText().toString();
                password=etpassword.getText().toString();
                name=etname.getText().toString();
                mobile=etmobile.getText().toString();
                address=etaddress.getText().toString();

                createUser();


            }
        });




    }
    public void createUser()
    {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            uid=user.getUid();
                            Info.curuser=new User(name,address,mobile,email,uid,initialOrders);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("user").document(Info.curuser.getUid()).set(Info.curuser);


                            Toast.makeText(SignupActivity.this, "Authentication Successfull",
                                    Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(SignupActivity.this,HomeActivity.class);
                            startActivity(i);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });

    }
}