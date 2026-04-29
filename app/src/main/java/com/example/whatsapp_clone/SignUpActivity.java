package com.example.whatsapp_clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    EditText editname,editemail,editpassword,editconpassword,editmobile;
    Button signupbtn;

    TextView alreadyaccout,signupwithphone;
    String email="",name="",password="",conpassword="",mobile="";

    String currentTime;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editname = findViewById(R.id.editname);
        editemail = findViewById(R.id.editemail);
        editpassword = findViewById(R.id.editpassword);
        alreadyaccout = findViewById(R.id.alreadyacount);
        signupwithphone = findViewById(R.id.signupwithphone);
        signupbtn = findViewById(R.id.signupbtn);
        editconpassword = findViewById(R.id.editconpassword);
        editmobile = findViewById(R.id.editmobile);

        mauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your Account!");
        progressDialog.setCancelable(false);



        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                email = editemail.getText().toString().trim();
                name = editname.getText().toString().trim();
                password = editpassword.getText().toString().trim();
                conpassword = editconpassword.getText().toString().trim();
                mobile = editmobile.getText().toString().trim();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                currentTime = sdf.format(new Date());

                if(name.equals("")){
                    editname.setError("Field id Required!");
                } else if (email.equals("")) {
                    editemail.setError("Field id Required!");
                } else if (mobile.equals("")) {
                    editmobile.setError("Field id Required!");
                } else if (mobile.length()<10) {
                    editmobile.setError("Mobile no. minimun length 10!");
                } else if (password.equals("")) {
                    editpassword.setError("Field id Required!");
                } else if (password.length()<6) {
                    editpassword.setError("Password minimun length 6");
                } else if (conpassword.equals("")) {
                    editconpassword.setError("Field id Required!");
                }else if (conpassword.length()<6) {
                    editconpassword.setError("Password minimun length 6");
                }
                else {

                    if(!password.equals(conpassword)){
                        editconpassword.setError("Confirm Password not matched!");
                    }else {
                        progressDialog.show();
                        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Intent next = new Intent(SignUpActivity.this, SigninActivity.class);

                                if(task.isSuccessful()){

                                    String user_email = task.getResult().getUser().getEmail().replace(".",",");

                                    database.getReference("AllUsers").child(user_email).setValue(new UsersModal(name,email,password,"",currentTime,"Hey👋, Tap to chat💬!",mobile,"Handing with friends😁","")).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), name + " SignUp Successfully👍🏿",Toast.LENGTH_SHORT).show();
                                            startActivity(next);
                                            finish();

                                        }
                                    });

                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }

                }

            }
        });

        alreadyaccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nexttosignin = new Intent(SignUpActivity.this,SigninActivity.class);
                startActivity(nexttosignin);
                finish();
            }
        });

        signupwithphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(SignUpActivity.this, SignUpwithPhoneActivity.class);
                startActivity(next);
                finish();
            }
        });


    }
}