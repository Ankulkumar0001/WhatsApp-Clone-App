package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

public class Show_Full_User_DP_Activity extends AppCompatActivity {

    MaterialToolbar show_fulldp_toolbar;
    ImageView full_size_dp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_full_user_dp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        show_fulldp_toolbar = findViewById(R.id.show_fulldp_materialToolbar);
        full_size_dp = findViewById(R.id.full_size_dp);

        show_fulldp_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        Intent next = getIntent();
        String name = next.getStringExtra("name");
        String imageurl = next.getStringExtra("imageurl");

        show_fulldp_toolbar.setTitle(name);

        Glide.with(getApplicationContext())
                .load(imageurl)
                .placeholder(R.drawable.userplaceeholder)
                .into(full_size_dp);

    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim,R.anim.second);
    }
}