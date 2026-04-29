package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class community extends Fragment {

   RecyclerView communityRecyclerView;

   MaterialToolbar communitymaterialToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        communityRecyclerView = view.findViewById(R.id.communityRecyclerView);
        communitymaterialToolbar = view.findViewById(R.id.communitymaterialToolbar);

        communitymaterialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.communitiessetting){
                    Intent next = new Intent(getContext(),SettingActivity.class);
                    startActivity(next);
                    return true;
                }
                return false;
            }
        });



        ArrayList<communitymodal> communitymodalArrayList = new ArrayList<communitymodal>();

        communitymodalArrayList.add(new communitymodal(R.drawable.tmmna,"Tammna kumari Traders","Hello Tammna my side vishal kumar","Today"));
        communitymodalArrayList.add(new communitymodal(R.drawable.ankul,"Ankul Singh Traders","Hello Ankul Singh my side vishal kumar","Yestarday"));
        communitymodalArrayList.add(new communitymodal(R.drawable.tmmna,"Tammna kumari Traders","Hello Tammna my side vishal kumar","Today"));
        communitymodalArrayList.add(new communitymodal(R.drawable.ankul,"Ankul Singh Traders","Hello Ankul Singh my side vishal kumar","Yestarday"));
        communitymodalArrayList.add(new communitymodal(R.drawable.tmmna,"Tammna kumari Traders","Hello Tammna my side vishal kumar","Today"));
        communitymodalArrayList.add(new communitymodal(R.drawable.ankul,"Ankul Singh Traders","Hello Ankul Singh my side vishal kumar","Yestarday"));



        communityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        communityAdapter communityAdapter = new communityAdapter(getContext(),communitymodalArrayList);
        communityRecyclerView.setAdapter(communityAdapter);

        return view ;
    }
}