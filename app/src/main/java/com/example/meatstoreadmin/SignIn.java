package com.example.meatstoreadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meatstoreadmin.Admin.AdminDashboard;
import com.example.meatstoreadmin.Models.Users;
import com.example.meatstoreadmin.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {
    EditText edPass,edphone;
    TextView tvForgotPassword;
    Button btnSignIn;
    private ProgressDialog loadingBar;


    private  String parentDbName="Admins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edPass=findViewById(R.id.edSettingsPass);
        btnSignIn=findViewById(R.id.btnSignIn);
        edphone=findViewById(R.id.edSettingsPhone);
        loadingBar = new ProgressDialog(this);




        Paper.init(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  password,phone;


                phone= edphone.getText().toString();
                password = edPass.getText().toString();

                //check if phone o password fields are empty

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please enter Phone Number!", Toast.LENGTH_LONG).show();
                    return;
                }
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                AllowAccessToAccount(phone,password);

            }
        });
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        Paper.book().write(Prevalent.UserPhoneKey, phone);
        Paper.book().write(Prevalent.UserPasswordKey, password);


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                                Toast.makeText(getApplicationContext(), "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(getApplicationContext (), AdminDashboard.class);

                                  Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);


                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

