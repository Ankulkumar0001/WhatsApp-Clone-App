package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chats extends Fragment {

    RecyclerView recyclerView;
    CardView searchchatbtn;
    LinearLayout chatlottie,endtoend;
    MaterialToolbar materialToolbar;
    View line;
    ArrayList<My_All_chats_Modal> arrayList;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    chatsadpater chatsadpater;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.chatrecyclerview);
        searchchatbtn = view.findViewById(R.id.searchchatbtn);
        materialToolbar = view.findViewById(R.id.materialToolbarchat);
        chatlottie = view.findViewById(R.id.chatlottie);
        line = view.findViewById(R.id.line);
        endtoend  = view.findViewById(R.id.endtoend);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        arrayList = new ArrayList<My_All_chats_Modal>();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        chatsadpater = new chatsadpater(getContext(),arrayList);
        recyclerView.setAdapter(chatsadpater);

        String email = mAuth.getCurrentUser().getEmail().replace(".",",");

        database.getReference("AllUsers")
                .child(email).child("MyAllUsers")
                .orderByChild("ispined")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    My_All_chats_Modal chats = dataSnapshot.getValue(My_All_chats_Modal.class);

                    if (chats.getIspined()) {
                        arrayList.add(0, chats);
                    } else {
                        arrayList.add(chats);
                    }
                }
                chatsadpater.notifyDataSetChanged();

                if(arrayList.size() != 0){
                    chatlottie.setVisibility(View.GONE);
                    line.setVisibility(View.VISIBLE);
                    endtoend.setVisibility(View.VISIBLE);
                }else {
                    chatlottie.setVisibility(View.VISIBLE);
                    line.setVisibility(View.GONE);
                    endtoend.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        chatsadpater.notifyDataSetChanged();




        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.newgroup){
                    Toast.makeText(getContext(),"New group",Toast.LENGTH_SHORT).show();
                } else if (id == R.id.qrcode) {
                    Toast.makeText(getContext(),"QR Code clicked",Toast.LENGTH_SHORT).show();
                } else if (id == R.id.camera) {
                    Toast.makeText(getContext(),"Camera clicked",Toast.LENGTH_SHORT).show();
                } else if (id == R.id.newcommunity) {
                    Snackbar.make(getContext(),view,"New Community Clicked",Snackbar.ANIMATION_MODE_FADE).show();
                } else if (id == R.id.broadcastlists) {
                    Snackbar.make(getContext(),view,"Broadcast list Clicked",Snackbar.ANIMATION_MODE_FADE).show();
                } else if (id == R.id.linkeddevices) {
                    Snackbar.make(getContext(),view,"Linked devices Clicked",Snackbar.ANIMATION_MODE_FADE).show();
                } else if (id == R.id.sharred) {
                    Snackbar.make(getContext(),view,"Sharred Clicked",Snackbar.ANIMATION_MODE_FADE).show();
                } else if (id ==  R.id.payments) {
                    Snackbar.make(getContext(),view,"Payments Clicked",Snackbar.ANIMATION_MODE_FADE).show();
                } else if (id == R.id.readall) {
                    Snackbar.make(getContext(),view,"Read all Clicked",Snackbar.ANIMATION_MODE_FADE).show();
                } else if (id == R.id.setting) {
                    Intent next = new Intent(getContext(),SettingActivity.class);
                    startActivity(next);
                    return  true;
                }

                return false;
            }
        });

        searchchatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getContext(), SearchcontactsandchatsActivity.class);
                startActivity(next);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Jab aap chat se wapas aayenge, ye adapter ko refresh kar dega
        if (chatsadpater != null) {
            chatsadpater.notifyDataSetChanged();
        }
    }
}