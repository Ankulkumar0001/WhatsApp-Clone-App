package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingActivity extends AppCompatActivity {

    ImageView backbtn,setting_search,setting_qr,setting_addaccount,search_back_btn,login_user_dp;
    TextView login_user_name,userabout;
    CardView instagrambtn,facebookbtn,threadsbtn,aimetabtn,search_layout;
    EditText search_contact_and_chats;

    LinearLayout account_setting,privacy_setting,list_setting,avatar_setting,chats_setting,broadcast_setting,notification_setting,
            storage_setting,accessibility_setting,app_language_setting,help_setting,invite_setting,appUpdate_setting,setting_layout,profile;

    FirebaseDatabase database;

    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backbtn = findViewById(R.id.backfromsettingbtn);
        setting_search = findViewById(R.id.search_setting);
        setting_qr = findViewById(R.id.setting_qr);
        setting_addaccount = findViewById(R.id.setting_addaccount);
        instagrambtn = findViewById(R.id.instagrambtn);
        facebookbtn = findViewById(R.id.facebookbtn);
        threadsbtn = findViewById(R.id.threadsbtn);
        aimetabtn = findViewById(R.id.aimetabtn);
        account_setting = findViewById(R.id.account_setting);
        privacy_setting = findViewById(R.id.privacy_setting);
        avatar_setting = findViewById(R.id.avatar_setting);
        list_setting =  findViewById(R.id.list_setting);
        chats_setting = findViewById(R.id.chats_setting);
        broadcast_setting = findViewById(R.id.broadcast_setting);
        notification_setting = findViewById(R.id.notification_setting);
        storage_setting = findViewById(R.id.storage_setting);
        accessibility_setting = findViewById(R.id.accessibility_setting);
        app_language_setting = findViewById(R.id.app_language_setting);
        help_setting = findViewById(R.id.help_setting);
        invite_setting = findViewById(R.id.invite_friend_setting);
        appUpdate_setting = findViewById(R.id.appUpdate_setting);
        search_layout = findViewById(R.id.search_layout);
        setting_layout = findViewById(R.id.setting_layout);
        search_contact_and_chats = findViewById(R.id.search_contact_and_chats);
        search_back_btn = findViewById(R.id.search_back_btn);
        profile = findViewById(R.id.profile);
        login_user_dp = findViewById(R.id.login_user_dp);
        login_user_name = findViewById(R.id.login_user_name);
        userabout = findViewById(R.id.userabout);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference("AllUsers").child(mAuth.getCurrentUser().getEmail().replace(".",",")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);
                    String imageurl = snapshot.child("imageurl").getValue(String.class);
                    String about = snapshot.child("about").getValue(String.class);

                    login_user_name.setText(name);
                    if(!about.equals("")){
                      userabout.setText(about);
                        int color = Color.parseColor("#000000");
                        userabout.setTextColor(color);
                    }else {
                        userabout.setText("Add About");
                        int color = Color.parseColor("#4CAE50");
                        userabout.setTextColor(color);
                    }

                    Glide.with(getApplicationContext())
                            .load(imageurl)
                            .placeholder(R.drawable.userplaceeholder)
                            .into(login_user_dp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setting_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.VISIBLE);
                setting_layout.setVisibility(View.GONE);

                if(search_layout.getVisibility() == View.VISIBLE){
                    search_contact_and_chats.requestFocus();
                }

                search_contact_and_chats.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(search_contact_and_chats, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                }, 200);

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(SettingActivity.this, ProfileActivity.class);
                startActivity(next);
                overridePendingTransition(R.anim.anim,R.anim.second);
            }
        });

        search_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.GONE);
                setting_layout.setVisibility(View.VISIBLE );
            }
        });

        setting_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"QR Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        setting_addaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"add new account",Toast.LENGTH_SHORT).show();
            }
        });

        account_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"account clicked",Toast.LENGTH_SHORT).show();
            }
        });

        privacy_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"privacy clicked",Toast.LENGTH_SHORT).show();
            }
        });

        avatar_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"avatar clicked",Toast.LENGTH_SHORT).show();
            }
        });

        list_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"list clicked",Toast.LENGTH_SHORT).show();
            }
        });

        chats_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"chats clicked",Toast.LENGTH_SHORT).show();
            }
        });

        broadcast_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"broadcast clicked",Toast.LENGTH_SHORT).show();
            }
        });

        notification_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"notification clicked",Toast.LENGTH_SHORT).show();
            }
        });

        storage_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"storage clicked",Toast.LENGTH_SHORT).show();
            }
        });

        accessibility_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"accessibility clicked",Toast.LENGTH_SHORT).show();
            }
        });

        app_language_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"language clicked",Toast.LENGTH_SHORT).show();
            }
        });

        help_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"help clicked",Toast.LENGTH_SHORT).show();
            }
        });

        invite_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"invite clicked",Toast.LENGTH_SHORT).show();
            }
        });

        appUpdate_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"app update clicked",Toast.LENGTH_SHORT).show();
            }
        });

        instagrambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"instagram clicked",Toast.LENGTH_SHORT).show();
            }
        });

        facebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"facebook clicked",Toast.LENGTH_SHORT).show();
            }
        });

        threadsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"threads clicked",Toast.LENGTH_SHORT).show();
            }
        });

        aimetabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"meta ai clicked",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}