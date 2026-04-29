package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View_User_Profile extends AppCompatActivity {

    ImageView backbtn,userimage,openmenu,big_image;
    TextView username,big_name,view_about,view_email,view_mobile,group_name,block_name,report_name;
    LinearLayout topbar,toolbar;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backbtn = findViewById(R.id.backbtn);
        userimage = findViewById(R.id.view_user_profile_dp);
        openmenu = findViewById(R.id.view_user_profile_openmenu);
        username = findViewById(R.id.view_user_profile_username);
        topbar = findViewById(R.id.view_user_topbar);
        toolbar = findViewById(R.id.view_user_profile_toolbar);
        big_name = findViewById(R.id.view_big_name);
        view_about = findViewById(R.id.view_about);
        view_email = findViewById(R.id.view_email);
        view_mobile = findViewById(R.id.view_mobile);
        group_name = findViewById(R.id.group_name);
        report_name = findViewById(R.id.report_name);
        block_name = findViewById(R.id.block_name);
        big_image = findViewById(R.id.big_image);

        database = FirebaseDatabase.getInstance();

        Intent next = getIntent();
        String name = next.getStringExtra("name");
        String imageurl = next.getStringExtra("imageurl");
        String email = next.getStringExtra("email");


        database.getReference("AllUsers")
                .child(email.replace(".",","))
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mobile,about;

                mobile = snapshot.child("mobile").getValue(String.class);
                about = snapshot.child("about").getValue(String.class);

                view_about.setText(about);
                view_mobile.setText(mobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        username.setText(name);
        big_name.setText(name);
        view_email.setText(email);
        group_name.setText(name);
        report_name.setText(name);
        block_name.setText(name);
        Glide.with(getApplicationContext())
                        .load(imageurl)
                        .placeholder(R.drawable.userplaceeholder)
                        .into(userimage);

        Glide.with(getApplicationContext())
                        .load(imageurl)
                        .placeholder(R.drawable.userplaceeholder)
                        .into(big_image);

        big_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(View_User_Profile.this,Show_Full_User_DP_Activity.class);
                next.putExtra("name",name);
                next.putExtra("imageurl",imageurl);
                startActivity(next);
                overridePendingTransition(R.anim.anim,R.anim.second);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        topbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(View_User_Profile.this,Show_Full_User_DP_Activity.class);
                next.putExtra("name",name);
                next.putExtra("imageurl",imageurl);
                startActivity(next);
                overridePendingTransition(R.anim.anim,R.anim.second);
            }
        });

        openmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContextThemeWrapper ctw = new ContextThemeWrapper(getApplicationContext(), R.style.MyPopupMenu2);
                PopupMenu popupMenu = new PopupMenu(ctw,view);
                popupMenu.getMenuInflater().inflate(R.menu.view_user_profile_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if(id == R.id.share){
                            Toast.makeText(getApplicationContext(),"share chicked",Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.edit) {
                            Toast.makeText(getApplicationContext(),"edit chicked",Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.verify_security_code) {
                            Toast.makeText(getApplicationContext(),"verify code chicked",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}