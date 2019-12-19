package com.example.meatstoreadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meatstoreadmin.Admin.AdminDashboard;
import com.example.meatstoreadmin.Models.Meat;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView meatName,meatPrice,Category;
    ImageView meatImage;
    Button applychanges,deleteProduct;
    private Uri ImageUri;
    String pid="";
    String price;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private static final int GalleryPick = 1;
    private String   Description, Price, Pname , saveCurrentDate, saveCurrentTime;;
    private String   downloadImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        meatName=findViewById(R.id.meatName);
        meatPrice=findViewById(R.id.meatPrice);
        Category=findViewById(R.id.Category);
        meatImage=findViewById(R.id.meatImage);

        applychanges=findViewById(R.id.applychanges);
        deleteProduct=findViewById(R.id.deleteProduct);

        //deleting product from database
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");
                ProductRef.child(pid).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Removed Successfully", Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });


        pid=getIntent().getStringExtra("pid");

        getProductDetails(pid);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        //To change product image
        meatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        applychanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidateProductData();


            }
        });
    }

    private void SaveProductInfoToDatabase() {



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", pid);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("meatImage", downloadImageUrl);

        productMap.put("meatPrice", Price);
        productMap.put("meatName", Pname);

        ProductsRef.child(pid).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                            startActivity(intent);


                            Toast.makeText(getApplicationContext(), "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            meatImage.setImageURI(ImageUri);
            StoreProductInformation();

        }
    }

    private void ValidateProductData()
    {

        Price = meatPrice.getText().toString();
        Pname = meatName.getText().toString();




      if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SaveProductInfoToDatabase();
        }
    }

    private void StoreProductInformation() {

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + pid + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(getApplicationContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(getApplicationContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void getProductDetails(String pid) {

        DatabaseReference ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");
        ProductRef.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Meat meat=dataSnapshot.getValue(Meat.class);

                    meatName.setText(meat.getMeatName());

                    price=meat.getMeatPrice().toString();

                    meatPrice.setText(price);

                    String category=meat.getCategory().toUpperCase();
                    Category.setText(category);
                    Picasso.get().load(meat.getMeatImage()).into(meatImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
