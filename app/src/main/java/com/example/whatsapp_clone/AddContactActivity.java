package com.example.whatsapp_clone;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    RecyclerView frequentlycontactRecylerview,contactonwhatsappRecylerview,invitewhatsappRecylerview;
    MaterialToolbar addcontactmaterialToolbar;

    LinearLayout conatct_option,share,contacthelp,lottie,freqlottie,new_group,new_contact,new_community;
    TextView frequentlytext,invitewhatsapptext;
    ImageView add_conatact_qr,cross;

    EditText search_conatct;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    contact_on_whatsapp_Adapter contactadpater;
    frequentlycontactAdpter freq_conatct_adapter;
    ArrayList<All_Users_Modal> conatctarrylist;
    ArrayList<My_All_chats_Modal> freq_contact_arrlist;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frequentlycontactRecylerview = findViewById(R.id.frequentlycontactRecylerview);
        contactonwhatsappRecylerview = findViewById(R.id.contactonwhatsappRecylerview);
        invitewhatsappRecylerview = findViewById(R.id.invitewhatsappRecylerview);
        addcontactmaterialToolbar = findViewById(R.id.addcontactmaterialToolbar);
        add_conatact_qr = findViewById(R.id.add_conatact_qr);
        conatct_option = findViewById(R.id.contact_option);
        share  = findViewById(R.id.sharecontact);
        contacthelp = findViewById(R.id.contacthelp);
        frequentlytext = findViewById(R.id.frequently_text);
        invitewhatsapptext = findViewById(R.id.invitewhatsapptext);
        search_conatct = findViewById(R.id.search_conatct);
        cross = findViewById(R.id.cross);
        lottie = findViewById(R.id.lottie);
        freqlottie = findViewById(R.id.freqlottie);
        new_group = findViewById(R.id.new_group);
        new_contact = findViewById(R.id.new_contact);
        new_community = findViewById(R.id.new_community);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        addcontactmaterialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.newbroadcast){
                    Toast.makeText(getApplicationContext(),"new broadcast",Toast.LENGTH_SHORT).show();
                    return  true;
                } else if (id == R.id.contactsetting) {
                    Toast.makeText(getApplicationContext(),"contact setting",Toast.LENGTH_SHORT).show();
                    return  true;
                } else if (id == R.id.Inviteafriend) {
                    Toast.makeText(getApplicationContext(),"invite a friend",Toast.LENGTH_SHORT).show();
                    return  true;
                } else if (id == R.id.contacts) {
                    Toast.makeText(getApplicationContext(),"contact",Toast.LENGTH_SHORT).show();
                    return  true;
                } else if (id == R.id.refresh) {
                    Toast.makeText(getApplicationContext(),"refresh",Toast.LENGTH_SHORT).show();
                    return  true;
                } else if (id == R.id.help) {
                    Toast.makeText(getApplicationContext(),"help",Toast.LENGTH_SHORT).show();
                    return  true;
                }
                return false;
            }
        });

        new_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddContactActivity.this, "new group", Toast.LENGTH_SHORT).show();
            }
        });

        new_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(AddContactActivity.this, Add_new_contact_activity.class);
                startActivity(next);
                finish();
            }
        });

        new_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddContactActivity.this, "new community", Toast.LENGTH_SHORT).show();
            }
        });

        //        load data form firebase
        conatctarrylist = new ArrayList<All_Users_Modal>();
        database.getReference("AllUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                conatctarrylist.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    All_Users_Modal chatsmodel = dataSnapshot.getValue(All_Users_Modal.class);
                    if(!chatsmodel.email.equals(mAuth.getCurrentUser().getEmail())){
                        conatctarrylist.add(chatsmodel);
                    }
                }
                contactadpater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        contactonwhatsappRecylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        contactadpater = new contact_on_whatsapp_Adapter(AddContactActivity.this,conatctarrylist);
        contactonwhatsappRecylerview.setAdapter(contactadpater);
        contactadpater.notifyDataSetChanged();



        freq_contact_arrlist = new ArrayList<My_All_chats_Modal>();
        database.getReference("AllUsers").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("MyAllUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                freq_contact_arrlist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    My_All_chats_Modal data = dataSnapshot.getValue(My_All_chats_Modal.class);
                    freq_contact_arrlist.add(data);
                }
                freq_conatct_adapter.notifyDataSetChanged();

                if(freq_contact_arrlist.size() != 0){
                    freqlottie.setVisibility(View.GONE);
                }else {
                    freqlottie.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        frequentlycontactRecylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        freq_conatct_adapter = new frequentlycontactAdpter(AddContactActivity.this,freq_contact_arrlist,1);
        frequentlycontactRecylerview.setAdapter(freq_conatct_adapter);


        addcontactmaterialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add_conatact_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"qr clicked",Toast.LENGTH_SHORT).show();
            }
        });

        search_conatct.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString().trim();
                ArrayList<All_Users_Modal> newlist = new ArrayList<All_Users_Modal>();

                if(text.length()>0){

                    conatct_option.setVisibility(View.GONE);
                    cross.setVisibility(View.VISIBLE);
                    frequentlytext.setVisibility(View.GONE);
                    frequentlycontactRecylerview.setVisibility(View.GONE);
                    invitewhatsapptext.setVisibility(View.GONE);
                    invitewhatsappRecylerview.setVisibility(View.GONE);
                    share.setVisibility(View.GONE);
                    contacthelp.setVisibility(View.GONE);
                    freqlottie.setVisibility(View.GONE);

                    for (i = 0; i < conatctarrylist.size(); i++){
                        if(conatctarrylist.get(i).getName().toLowerCase().contains(text.toLowerCase())){
                            All_Users_Modal data = new All_Users_Modal();
                            data.setName(conatctarrylist.get(i).getName());
                            data.setAbout(conatctarrylist.get(i).getAbout());
                            data.setimageurl(conatctarrylist.get(i).getimageurl());
                            data.setEmail(conatctarrylist.get(i).getEmail());
                            newlist.add(data);
                        }
                    }

                    if(newlist.size() == 0){
                        lottie.setVisibility(View.VISIBLE);
                    }else {
                        lottie.setVisibility(View.GONE);
                    }
                    contactonwhatsappRecylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    contactadpater = new contact_on_whatsapp_Adapter(AddContactActivity.this,newlist);
                    contactonwhatsappRecylerview.setAdapter(contactadpater);
                    contactadpater.notifyDataSetChanged();

                }else {

                    conatct_option.setVisibility(View.VISIBLE);
                    frequentlytext.setVisibility(View.VISIBLE);
                    frequentlycontactRecylerview.setVisibility(View.VISIBLE);
                    invitewhatsapptext.setVisibility(View.VISIBLE);
                    invitewhatsappRecylerview.setVisibility(View.VISIBLE);
                    share.setVisibility(View.VISIBLE);
                    cross.setVisibility(View.GONE);
                    contacthelp.setVisibility(View.VISIBLE);
                    lottie.setVisibility(View.GONE);
                    freqlottie.setVisibility(View.GONE);

                    contactonwhatsappRecylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    contactadpater = new contact_on_whatsapp_Adapter(AddContactActivity.this,conatctarrylist);
                    contactonwhatsappRecylerview.setAdapter(contactadpater);
                    contactadpater.notifyDataSetChanged();
                }
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_conatct.setText("");
            }
        });

    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}