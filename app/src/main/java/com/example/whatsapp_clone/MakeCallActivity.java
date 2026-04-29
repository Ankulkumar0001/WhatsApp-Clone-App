package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class MakeCallActivity extends AppCompatActivity {

    RecyclerView frequently_call_Recylerview,forcall_contactonwhatsappRecylerview,inviteforwhatsapp_call_Recylerview;
    ImageView backfromsmakecall,searchcontact,newcontactqr,search_back_btn;
    LinearLayout contact_toolbar;
    CardView search_layout;
    EditText search_contact_and_chats;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    frequentlycontactAdpter frequently_contact_Adpter;
    ArrayList<My_All_chats_Modal> freq_contact_arrlist;
    call_contact_on_whatsapp_adapter call_conatct_onwhatsapp_adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_call);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frequently_call_Recylerview = findViewById(R.id.frequently_call_Recylerview);
        forcall_contactonwhatsappRecylerview = findViewById(R.id.forcall_contactonwhatsappRecylerview);
        inviteforwhatsapp_call_Recylerview = findViewById(R.id.inviteforwhatsapp_call_Recylerview);
        backfromsmakecall = findViewById(R.id.backfromsmakecall);
        searchcontact = findViewById(R.id.searchcontacts);
        newcontactqr = findViewById(R.id.newcontactsqr);
        search_layout = findViewById(R.id.search_layout);
        search_back_btn = findViewById(R.id.search_back_btn);
        contact_toolbar = findViewById(R.id.contact_toolbar);
        search_contact_and_chats = findViewById(R.id.search_contact_and_chats);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        freq_contact_arrlist = new ArrayList<My_All_chats_Modal>();
        database.getReference("AllUsers").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("MyAllUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                freq_contact_arrlist.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    My_All_chats_Modal data = dataSnapshot.getValue(My_All_chats_Modal.class);
                    freq_contact_arrlist.add(data);
                }
                frequently_contact_Adpter.notifyDataSetChanged();
                call_conatct_onwhatsapp_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        frequently_call_Recylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        frequently_contact_Adpter = new frequentlycontactAdpter(MakeCallActivity.this,freq_contact_arrlist,0);
        frequently_call_Recylerview.setAdapter(frequently_contact_Adpter);


        forcall_contactonwhatsappRecylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        call_conatct_onwhatsapp_adapter = new call_contact_on_whatsapp_adapter(MakeCallActivity.this,freq_contact_arrlist);
        forcall_contactonwhatsappRecylerview.setAdapter(call_conatct_onwhatsapp_adapter);



        backfromsmakecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.VISIBLE);
                contact_toolbar.setVisibility(View.GONE);

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

        search_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.GONE);
                contact_toolbar.setVisibility(View.VISIBLE);
            }
        });

        newcontactqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"contact QR clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}