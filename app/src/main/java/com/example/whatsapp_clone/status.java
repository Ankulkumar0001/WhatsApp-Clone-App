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


public class status extends Fragment {

    RecyclerView statusRecylerView,channelRecyclerView,channelListRecyclerView;
    MaterialToolbar statusmaterialToolbar;

    ImageView search_back_btn;
    CardView search_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_status, container, false);

       statusRecylerView = view.findViewById(R.id.statusreccyclerview);
       channelRecyclerView = view.findViewById(R.id.chennalRecyclerView);
       channelListRecyclerView = view.findViewById(R.id.chennallistRecyclerView);
       statusmaterialToolbar = view.findViewById(R.id.statusmaterialToolbar);
       search_layout = view.findViewById(R.id.search_layout);
       search_back_btn = view.findViewById(R.id.search_back_btn);

       statusmaterialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
              int id = item.getItemId();
              if(id == R.id.statuscreatechannel){
               Toast.makeText(getContext(),"status create channel",Toast.LENGTH_SHORT).show();
               return  true;
              } else if (id == R.id.statussearch) {
                  search_layout.setVisibility(View.VISIBLE);
                  statusmaterialToolbar.setVisibility(View.GONE);
                  return  true;
              } else if (id == R.id.statusprivacy) {
               Toast.makeText(getContext(),"status privacy",Toast.LENGTH_SHORT).show();
               return  true;
              } else if (id == R.id.statusstarred) {
               Toast.makeText(getContext(),"status starred",Toast.LENGTH_SHORT).show();
               return  true;
              } else if (id == R.id.statusadpre) {
               Toast.makeText(getContext(),"status adpre",Toast.LENGTH_SHORT).show();
               return  true;
              } else if (id == R.id.statussetting) {
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
               statusmaterialToolbar.setVisibility(View.VISIBLE);
           }
       });


        ArrayList<statusmodal> arrayList = new ArrayList<statusmodal>();

        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Nakul kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Vishal kumar"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Ankul kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Vikash kumar"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Yogesh kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Kriti Malviya"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Deepa Singh"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Nakul kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Vishal kumar"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Ankul kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Vikash kumar"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Yogesh kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Kriti Malviya"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Deepa Singh"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Nakul kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Vishal kumar"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Ankul kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Vikash kumar"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Yogesh kumar"));
        arrayList.add(new statusmodal(R.drawable.ankul,R.drawable.tmmna,"Kriti Malviya"));
        arrayList.add(new statusmodal(R.drawable.tmmna,R.drawable.ankul,"Deepa Singh"));

        statusRecylerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));

        StatusAdapter adapter = new StatusAdapter(getContext(),arrayList);
        statusRecylerView.setAdapter(adapter);


        // Channel RecyclerView

        ArrayList<All_Users_Modal> channelarrlist = new ArrayList<All_Users_Modal>();

//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.google));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.tmmna));
//        channelarrlist.add(new chatsmodel("Vishal Kumar","Hello vishal me ankul singh","10:22 PM",R.drawable.ankul));



        channelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        channelAdapter channelAdapter = new channelAdapter(getContext(),channelarrlist);
        channelRecyclerView.setAdapter(channelAdapter);


        //Channel List Adapter Recycler View

        ArrayList<channellistmodal> channellistmodalArrayList = new ArrayList<channellistmodal>();

        channellistmodalArrayList.add(new channellistmodal(R.drawable.ankul,"BTEUP","350k"));
        channellistmodalArrayList.add(new channellistmodal(R.drawable.tmmna,"CBSE","880k"));
        channellistmodalArrayList.add(new channellistmodal(R.drawable.google,"Google","750k"));
        channellistmodalArrayList.add(new channellistmodal(R.drawable.whatsapp,"Whatsapp","2350k"));
        channellistmodalArrayList.add(new channellistmodal(R.drawable.ankul,"Ankul Singh","50k"));
        channellistmodalArrayList.add(new channellistmodal(R.drawable.tmmna,"Tammna kumari","920k"));
        channellistmodalArrayList.add(new channellistmodal(R.drawable.whatsapp,"Vikash Yadav","850k"));

        channelListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        channelListAdpter channelListAdpter = new channelListAdpter(getContext(),channellistmodalArrayList);
        channelListRecyclerView.setAdapter(channelListAdpter);

        return view;
    }
}