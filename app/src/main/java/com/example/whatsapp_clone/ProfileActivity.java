package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    MaterialToolbar profilematerialToolbar;
    ImageView userimage;
    TextView editbtn,login_user_name,Login_user_link,login_user_about,login_user_mobile,getLogin_user_email;
    CardView logout_btn;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profilematerialToolbar = findViewById(R.id.profilematerialToolbar);
        editbtn = findViewById(R.id.edit);
        login_user_name = findViewById(R.id.currentloginusername);
        userimage = findViewById(R.id.userdp);
        login_user_about = findViewById(R.id.login_user_about);
        Login_user_link = findViewById(R.id.login_user_link);
        login_user_mobile = findViewById(R.id.login_user_mobile);
        logout_btn = findViewById(R.id.logout_btn);
        getLogin_user_email = findViewById(R.id.login_user_email);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        database.getReference("AllUsers").child(mAuth.getCurrentUser().getEmail().replace(".",",")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);
                    String imageurl = snapshot.child("imageurl").getValue(String.class);
                    String about = snapshot.child("about").getValue(String.class);
                    String link = snapshot.child("link").getValue(String.class);
                    String mobile = snapshot.child("mobile").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    login_user_name.setText(name);
                    if(!about.equals("")){
                        login_user_about.setText(about);
                    }else {
                        login_user_about.setText("Add About");
                    }

                    if(!link.equals("")) {
                        Login_user_link.setText(link);
                    }else {
                        Login_user_link.setText("Add link");
                    }

                    login_user_mobile.setText(mobile);
                    getLogin_user_email.setText(email);

                    Glide.with(getApplicationContext())
                            .load(imageurl)
                            .placeholder(R.drawable.userplaceeholder)
                            .into(userimage);

                    userimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProfileActivity.this,Show_Full_User_DP_Activity.class);
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

        profilematerialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.anim,R.anim.second);

            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(ProfileActivity.this,Edit_Current_User_Activity.class);
                startActivity(next);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("userstatus",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                FirebaseAuth.getInstance().signOut();

                editor.putString("email","");
                editor.putString("password","");
                editor.putBoolean("islogin",false);
                editor.clear();
                editor.apply();
                Intent intent = new Intent(ProfileActivity.this, SigninActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}