package com.example.whatsapp_clone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpwithPhoneActivity extends AppCompatActivity {

    TextView signupwithemail,alreadyacountphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_upwith_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signupwithemail = findViewById(R.id.signupwithemail);
        alreadyacountphone = findViewById(R.id.alreadyacountphone);

        signupwithemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(SignUpwithPhoneActivity.this, SignUpActivity.class);
                startActivity(next);
                finish();
            }
        });

        alreadyacountphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(SignUpwithPhoneActivity.this, SigninActivity.class);
                startActivity(next);
                finish();
            }
        });
    }
}