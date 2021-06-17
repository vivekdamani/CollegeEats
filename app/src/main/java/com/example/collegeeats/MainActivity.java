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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.collegeeats.model.Info;
import com.example.collegeeats.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    
    private Button Signup;
    EditText lemail,lpassword;
    public String l_email,l_password;
    Button Login;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Signup= (Button) findViewById(R.id.Sign_Up);
        Login=(Button) findViewById(R.id.login);
        lemail= findViewById(R.id.EmailAddress);
        lpassword = findViewById(R.id.Password);

        progressBar=new ProgressDialog(this);

        progressBar.setCancelable(true);
        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_email=lemail.getText().toString();
                l_password=lpassword.getText().toString();
//                l_email="def@gmail.com";
//                l_password="defdef";
//                l_email="abc@gmail.com";
//                l_password="abcabc";
                loginUser(mAuth);

            }
        });



    }
    public void loginUser(FirebaseAuth mAuth)
    {

        progressBar.setTitle("Logging in.. Please wait!!");
        progressBar.show();



        mAuth.signInWithEmailAndPassword(l_email , l_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressBar.dismiss();

                            if(l_email.equals("abc@gmail.com"))
                            {
                                Intent i=new Intent(MainActivity.this,SellerActivity.class);
                                startActivity(i);
                            }
                            else {
                                String email,mobile,address,name,uid;


                                uid=user.getUid();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                DocumentReference docRef = db.collection("user").document(uid);
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                        Info.curuser=new User();
                                        Info.curuser = documentSnapshot.toObject(User.class);
                                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });



                            }
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithEmail:failure", task.getException());
                            progressBar.dismiss();
                            Toast.makeText(MainActivity.this, "Login failed!!!",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }
}