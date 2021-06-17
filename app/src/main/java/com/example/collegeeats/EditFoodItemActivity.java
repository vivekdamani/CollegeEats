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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.collegeeats.model.Info;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditFoodItemActivity extends AppCompatActivity {
    TextView tv;
    EditText et;
    Button update,delete;
    ImageView ima;
    boolean avail=true;
    RadioButton r1,r2;



    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_item);
        tv=findViewById(R.id.nameofitem);
        et=findViewById(R.id.edit_price);
        update=findViewById(R.id.update_item);
        delete=findViewById(R.id.delete_info);
        ima=findViewById(R.id.imageofitem);
        r1=findViewById(R.id.radioY);
        r2=findViewById(R.id.radioN);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Updating Item...");


        tv.setText(Info.curitem.getName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        et.setText(String.valueOf(Info.curitem.getPrice()));
        ima.setVisibility(View.VISIBLE);
        Glide.with(EditFoodItemActivity.this).load(Info.curitem.getImage()).apply(options).into(ima);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference dr=db.collection("items").document(Info.curitem.getId());

                Info.curitem.setAvail(avail);
                Info.curitem.setPrice(Integer.parseInt(String.valueOf(et.getText())));
                dr.set(Info.curitem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(EditFoodItemActivity.this,"Item edited..",Toast.LENGTH_SHORT).show();
                        onBackPressed();


                    }
                });


            }
        });

        avail=Info.curitem.isAvail();
        if(avail)
        {
            r1.toggle();
            onRadioButtonClicked((View)r1);
        }
        else
        {
            r2.toggle();
            onRadioButtonClicked((View)r2);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference dr=db.collection("items").document(Info.curitem.getId());

                        dr.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("aaa", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("aaa", "Error deleting document", e);
                            }
                        });


            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioY:
                if (checked)
                    avail=true;
                    break;
            case R.id.radioN:
                if (checked)
                    avail=false;
                    break;


        }
    }
}