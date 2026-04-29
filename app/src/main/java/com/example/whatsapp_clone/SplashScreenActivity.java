package com.example.whatsapp_clone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth mauth;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        mauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        SharedPreferences sharedPreferences = getSharedPreferences("userstatus",MODE_PRIVATE);
        Boolean login =  sharedPreferences.getBoolean("islogin",false);
        String email = sharedPreferences.getString("email","");
        String password = sharedPreferences.getString("password","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(login){

                    mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent next2 =  new Intent(SplashScreenActivity.this,MainActivity.class);
                                startActivity(next2);
                                finish();
                            }else
                            {
                                Intent next =  new Intent(SplashScreenActivity.this,SigninActivity.class);
                                startActivity(next);
                                finish();
                            }

                        }
                    });



                }else {
                    Intent next =  new Intent(SplashScreenActivity.this,SigninActivity.class);
                    startActivity(next);
                    finish();
                }
            }
        },1000);

    }
}