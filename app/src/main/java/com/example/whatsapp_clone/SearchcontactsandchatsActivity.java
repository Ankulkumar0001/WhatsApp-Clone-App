package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchcontactsandchatsActivity extends AppCompatActivity {

    RecyclerView search_contact_and_chats_recyclerview;

    EditText searchchatandcontact;

    ImageView search_back_btn;

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    ArrayList<My_All_chats_Modal> arrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_searchcontactsandchats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        search_contact_and_chats_recyclerview = findViewById(R.id.search_contact_and_chats_recyclerview);
        searchchatandcontact = findViewById(R.id.search_contact_and_chats);
        search_back_btn = findViewById(R.id.search_back_btn);
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        arrayList = new ArrayList<My_All_chats_Modal>();

        search_contact_and_chats_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        chatsadpater chatsadpater = new chatsadpater(SearchcontactsandchatsActivity.this,arrayList);
        search_contact_and_chats_recyclerview.setAdapter(chatsadpater);

        database.getReference("AllUsers").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("MyAllUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    My_All_chats_Modal chat = dataSnapshot.getValue(My_All_chats_Modal.class);
                    arrayList.add(chat);
                }
                chatsadpater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}