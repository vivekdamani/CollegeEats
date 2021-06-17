package com.example.collegeeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegeeats.model.Items;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {
    Button AddImage,UploadItem;
    public static final int RESULT_LOAD_IMAGE = 1;
    ImageView ItemImage;
   // private static final int SELECT_PICTURE = 100;
    int SELECT_PICTURE = 200;
    private Uri selectedImageUri;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;


    String imgLink;
    private String name;
    private int price;
    EditText ETn,ETp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ItemImage=findViewById(R.id.ItemImage);
        AddImage=(Button) findViewById(R.id.AddImage);
        UploadItem=findViewById(R.id.uploadItem);
        ETn=findViewById(R.id.ItemName);
        ETp=findViewById(R.id.ItemPrice);



        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageChooser();

            }
        });

            UploadItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage();
                }
            });
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    ItemImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void uploadFirestore()
    {
        Items item=new Items();
        item.setAvail(true);
        item.setImage(imgLink);
        item.setName(ETn.getText().toString());
        item.setPrice(Integer.parseInt(ETp.getText().toString()));
        item.setId(UUID.randomUUID().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("items").document(item.getId()).set(item);


    }

    private void uploadImage()
    {
        if (selectedImageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString()+".jpeg");

            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {


                                    //Uri i=taskSnapshot.getUploadSessionUri();


                                    //taskSnapshot.getMetadata().getPath();
                                   // imgLink=taskSnapshot.getUploadSessionUri().toString();
                                    // Image uploaded successfully
                                    // Dismiss dialog





                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imgLink=uri.toString();
                                            uploadFirestore();
                                            Log.d("aaaa",imgLink);
                                        }
                                    });
//                                    imgLink=ref.getDownloadUrl().toString();
//                                    uploadFirestore();
                                    progressDialog.dismiss();

//                                    Log.d("aaaa",ref.getDownloadUrl().toString());
//
                                    Toast.makeText(AddItemActivity.this,
                                                      "Item added successfully",
                                                    Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddItemActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

}