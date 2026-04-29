package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_new_contact_activity extends AppCompatActivity {

    MaterialToolbar materialToolbar;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    EditText name,mobile,email;
    Button savebtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        materialToolbar = findViewById(R.id.addnewcontactmaterialToolbar);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobilenumber);
        email = findViewById(R.id.emailid);
        savebtn  = findViewById(R.id.savebtn);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.qr){
                    Toast.makeText(Add_new_contact_activity.this, "qr clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String useremail,username,usermobile;
                useremail = email.getText().toString().trim();
                username = name.getText().toString().trim();
                usermobile = mobile.getText().toString().trim();

                if(username.isEmpty()){
                    name.setError("Field is reuired!");
                } else if (usermobile.isEmpty()) {
                    mobile.setError("Field is reuired!");
                } else if (usermobile.length()<10) {
                    mobile.setError("Mobile number minimum length 10");
                } else if (useremail.isEmpty()) {
                    email.setError("Field is reuired!");
                }else {
                    database.getReference("AllUsers")
                            .child(useremail.replace(".",","))
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){

                                        database.getReference("AllUsers")
                                                .child(mAuth.getCurrentUser().getEmail().replace(".",","))
                                                .child("MyAllUsers")
                                                .child(useremail.replace(".",","))
                                                .setValue(new My_All_chats_Modal(useremail,false,false,false));

                                        Toast.makeText(Add_new_contact_activity.this, username + " is added in your chat.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Add_new_contact_activity.this,AddContactActivity.class);
                                        startActivity(intent);
                                        finish();


                                    }else {
                                        Toast.makeText(Add_new_contact_activity.this, username + " is not register on this application please invite first.", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }

            }
        });

    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}