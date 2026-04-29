package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;


public class call extends Fragment {

    RecyclerView callsRecyclerView;

    CardView voicecall,schedulecalls,keyboard,favourites,search_layout;

    ImageView search_back_btn;

    MaterialToolbar callmaterialToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_call, container, false);

        callsRecyclerView = view.findViewById(R.id.callsRecyclerView);
        callmaterialToolbar = view.findViewById(R.id.callmaterialToolbar);
        voicecall = view.findViewById(R.id.voicecall);
        schedulecalls = view.findViewById(R.id.scheduledcalls);
        keyboard = view.findViewById(R.id.keyboard);
        favourites = view.findViewById(R.id.favourites);
        search_layout = view.findViewById(R.id.search_layout);
        search_back_btn = view.findViewById(R.id.search_back_btn);

        callmaterialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.clearcalllog){
                    Toast.makeText(getContext(),"clear call log",Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.callssearch) {
                    search_layout.setVisibility(View.VISIBLE);
                    callmaterialToolbar.setVisibility(View.GONE);
                    return true;
                } else if (id == R.id.scheduledcalls) {
                    Toast.makeText(getContext(),"scheduled calls",Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.callssettings) {
                    Intent next = new Intent(getContext(),SettingActivity.class);
                    startActivity(next);
                    return  true;
                }
                return false;
            }
        });

        search_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.GONE);
                callmaterialToolbar.setVisibility(View.VISIBLE);
            }
        });

        voicecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"voice call clicked",Toast.LENGTH_SHORT).show();
            }
        });

        schedulecalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"schedule clicked",Toast.LENGTH_SHORT).show();
            }
        });

        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"keyboard clicked",Toast.LENGTH_SHORT).show();
            }
        });

        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"favourite clicked",Toast.LENGTH_SHORT).show();
            }
        });



        ArrayList<callslogmodal> callslogmodalArrayList = new ArrayList<>();

        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Kriti Malviya","23"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Ankul Singh","59"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Vishal Kumar","13"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Yogesh Pal","55"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Nakul kumar","20"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Deepa Singh","33"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Kriti Malviya","23"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Ankul Singh","59"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Vishal Kumar","13"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Yogesh Pal","55"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Nakul kumar","20"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Deepa Singh","33"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Kriti Malviya","23"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Ankul Singh","59"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Vishal Kumar","13"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Yogesh Pal","55"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Nakul kumar","20"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Deepa Singh","33"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Kriti Malviya","23"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Ankul Singh","59"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Vishal Kumar","13"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Yogesh Pal","55"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.tmmna,"Nakul kumar","20"));
        callslogmodalArrayList.add(new callslogmodal(R.drawable.ankul,"Deepa Singh","33"));

        callsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CallsAdpter callsAdpter = new CallsAdpter(getContext(),callslogmodalArrayList);
        callsRecyclerView.setAdapter(callsAdpter);

        return view;
    }
}