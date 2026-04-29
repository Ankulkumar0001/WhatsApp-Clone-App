package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Current_User_Activity extends AppCompatActivity {

    MaterialToolbar updatetoolbar;
    EditText update_image,update_name,update_link,update_about;
    Button update_btn;
    ImageView loginuserdp;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextView counter;
    String editname="",editimageurl="",editlink="",editabout="";

    UsersModal usersModal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_current_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        updatetoolbar = findViewById(R.id.updatematerialToolbar);
        update_about = findViewById(R.id.update_about);
        update_image = findViewById(R.id.update_image);
        update_link = findViewById(R.id.update_links);
        update_name = findViewById(R.id.update_name);
        update_btn = findViewById(R.id.updatebtn);
        loginuserdp = findViewById(R.id.loginuserdp);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference("AllUsers").child(mAuth.getCurrentUser().getEmail().replace(".",",")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);
                    String imageurl = snapshot.child("imageurl").getValue(String.class);
                    String about = snapshot.child("about").getValue(String.class);
                    String link = snapshot.child("link").getValue(String.class);

                    update_name.setText(name);
                    update_image.setText(imageurl);
                    update_about.setText(about);
                    update_link.setText(link);

                    Glide.with(getApplicationContext())
                            .load(imageurl)
                            .placeholder(R.drawable.userplaceeholder)
                            .into(loginuserdp);

                    loginuserdp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Edit_Current_User_Activity.this,Show_Full_User_DP_Activity.class);
                            intent.putExtra("imageurl",imageurl);
                            intent.putExtra("name",name);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim,R.anim.second);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updatetoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        update_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int msglength = charSequence.length();
                if(msglength>25){
                    update_name.setError("name include only 25 charecters!");
                }

            }
        });

        update_about.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int msglength = charSequence.length();
                if(msglength>25){
                    update_about.setError("name include only 25 charecters!");
                }

            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editname = update_name.getText().toString().trim();
                editimageurl = update_image.getText().toString().trim();
                editabout = update_about.getText().toString().trim();
                editlink = update_link.getText().toString().trim();

                if(editname.isEmpty()){
                    update_name.setError("Field is Required!");
                } else if (editname.length()>25) {
                    Toast.makeText(getApplicationContext(),"Name include only 25 charecters!",Toast.LENGTH_SHORT).show();
                } else if (editimageurl.isEmpty()) {
                    update_image.setError("Field is Required!");
                } else if (editabout.isEmpty()) {
                    update_about.setError("Field is Required!");
                }else if (editabout.length()>25) {
                    Toast.makeText(getApplicationContext(),"About include only 25 charecters!",Toast.LENGTH_SHORT).show();
                }
                else if (editlink.isEmpty()) {
                    update_link.setError("Field is Required!");
                } else {
                    DatabaseReference reference=database.getReference("AllUsers").child(mAuth.getCurrentUser().getEmail().replace(".",","));
                    reference.child("imageurl").setValue(editimageurl).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                reference.child("name").setValue(editname);
                                reference.child("about").setValue(editabout);
                                reference.child("link").setValue(editlink);
                                Toast.makeText(getApplicationContext(),"Profile Update Successfully👍",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else {
                                Toast.makeText(getApplicationContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show();
                            }

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