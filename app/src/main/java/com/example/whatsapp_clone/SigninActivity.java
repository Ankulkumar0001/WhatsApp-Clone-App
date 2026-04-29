package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class SigninActivity extends AppCompatActivity {

    EditText editemailforlogin,editpasswordforlogin;
    Button signinbtn;
    TextView backtosignup,forgotpassword,signwithphonelogin;

    private FirebaseAuth mauth;
    String  email="",password="";

    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editemailforlogin = findViewById(R.id.editemailforlogin);
        editpasswordforlogin = findViewById(R.id.editpasswordforlogin);
        backtosignup = findViewById(R.id.backtosignup);
        signinbtn = findViewById(R.id.signinbtn);
        forgotpassword = findViewById(R.id.forgotpassword);
        signwithphonelogin = findViewById(R.id.signwithphonelogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("We'r login you");
        progressDialog.setCancelable(false);

        mauth = FirebaseAuth.getInstance();

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = editemailforlogin.getText().toString().trim();
                password = editpasswordforlogin.getText().toString().trim();

                if(email.equals("")){
                    editemailforlogin.setError("Field is Required!");
                } else if (password.equals("")) {
                    editpasswordforlogin.setError("Field is Required!");
                } else if (password.length()<6) {
                    editpasswordforlogin.setError("Password minimun lenght 6");
                } else {

                    progressDialog.show();
                    mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Login Successfully☺️",Toast.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences sharedPreferences = getSharedPreferences("userstatus",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("islogin",true);
                                        editor.putString("email",email);
                                        editor.putString("password",password);
                                        editor.apply();
                                        Intent nexttohome = new Intent(SigninActivity.this,MainActivity.class);
                                        startActivity(nexttohome);
                                        finish();
                                    }
                                },1000);
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

        backtosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backsignup = new Intent(SigninActivity.this, SignUpActivity.class);
                startActivity(backsignup);
                finish();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
                startActivity(next);

            }
        });

        signwithphonelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(SigninActivity.this, SignUpwithPhoneActivity.class);
                startActivity(next);
                finish();
            }
        });

    }
}