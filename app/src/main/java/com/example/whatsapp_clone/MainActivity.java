package com.example.whatsapp_clone;

import static com.example.whatsapp_clone.R.drawable.calling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    Button logout;

    CardView ai_and_addstatusbtn,addcontactbtn;
    ImageView  addcontactbtnimage,metaai,chatbtn,statusbtn,communitybtn,callbtn;
    LinearLayout chats,status,communities,calls;

    int flag = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        chatbtn = findViewById(R.id.chatbtn);
        statusbtn = findViewById(R.id.statusbtn);
        communitybtn = findViewById(R.id.communitybtn);
        callbtn = findViewById(R.id.callbtn);
        chats = findViewById(R.id.chats);
        status = findViewById(R.id.status);
        communities = findViewById(R.id.communities);
        calls = findViewById(R.id.calls);
        addcontactbtn = findViewById(R.id.addconatctbtn);
        ai_and_addstatusbtn = findViewById(R.id.writestatus);
        addcontactbtnimage = findViewById(R.id.addconatctbtnimage);
        logout = findViewById(R.id.logout);
        metaai = findViewById(R.id.metaai);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("userstatus",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent next = new Intent(MainActivity.this,SigninActivity.class);
                startActivity(next);
                finish();
            }
        });
        chats.setBackgroundColor(Color.parseColor("#D0E8D1"));
        chatbtn.setImageResource(R.drawable.chatforonclick);
        loadfragment(new Chats(),0);

        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = Color.parseColor("#D0E8D1");
                chats.setBackgroundColor(color);
                status.setBackgroundColor(Color.parseColor("#ffffff"));
                communities.setBackgroundColor(Color.parseColor("#ffffff"));
                calls.setBackgroundColor(Color.parseColor("#ffffff"));

                chatbtn.setImageResource(R.drawable.chatforonclick);
                statusbtn.setImageResource(R.drawable.status);
                communitybtn.setImageResource(R.drawable.group);
                callbtn.setImageResource(R.drawable.voicecall);

                ai_and_addstatusbtn.setVisibility(View.VISIBLE);
                addcontactbtnimage.setImageResource(R.drawable.addcontact);
                metaai.setImageResource(R.drawable.metaai);
                addcontactbtn.setVisibility(View.VISIBLE);
                loadfragment(new Chats(),0);

                flag = flag + 2;
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chats.setBackgroundColor(Color.parseColor("#ffffff"));
                status.setBackgroundColor(Color.parseColor("#D0E8D1"));
                communities.setBackgroundColor(Color.parseColor("#ffffff"));
                calls.setBackgroundColor(Color.parseColor("#ffffff"));

                chatbtn.setImageResource(R.drawable.comment);
                statusbtn.setImageResource(R.drawable.statusforonclick);
                communitybtn.setImageResource(R.drawable.group);
                callbtn.setImageResource(R.drawable.voicecall);

                ai_and_addstatusbtn.setVisibility(View.VISIBLE);
                addcontactbtnimage.setImageResource(R.drawable.addphoto);
                addcontactbtn.setVisibility(View.VISIBLE);
                metaai.setImageResource(R.drawable.pen);

                loadfragment(new status(),1);

                if(flag != 0 || flag != 1){
                    flag = flag * 0 + 1;
                }
            }
        });

        communities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chats.setBackgroundColor(Color.parseColor("#ffffff"));
                status.setBackgroundColor(Color.parseColor("#ffffff"));
                communities.setBackgroundColor(Color.parseColor("#D0E8D1"));
                calls.setBackgroundColor(Color.parseColor("#ffffff"));

                chatbtn.setImageResource(R.drawable.comment);
                statusbtn.setImageResource(R.drawable.status);
                communitybtn.setImageResource(R.drawable.communityforonclick);
                callbtn.setImageResource(R.drawable.voicecall);

                ai_and_addstatusbtn.setVisibility(View.INVISIBLE);
                addcontactbtn.setVisibility(View.INVISIBLE);

                loadfragment(new community(),1);
            }
        });

        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chats.setBackgroundColor(Color.parseColor("#ffffff"));
                status.setBackgroundColor(Color.parseColor("#ffffff"));
                communities.setBackgroundColor(Color.parseColor("#ffffff"));
                calls.setBackgroundColor(Color.parseColor("#D0E8D1"));

                chatbtn.setImageResource(R.drawable.comment);
                statusbtn.setImageResource(R.drawable.status);
                communitybtn.setImageResource(R.drawable.group);
                callbtn.setImageResource(R.drawable.callforonclick);

                ai_and_addstatusbtn.setVisibility(View.INVISIBLE);
                addcontactbtn.setVisibility(View.VISIBLE);
                addcontactbtnimage.setImageResource(calling);

                loadfragment(new call(),1);

                if(flag != 0){
                    flag = flag * 0;
                }
            }
        });

        addcontactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 0){

                    Intent next = new Intent(MainActivity.this,MakeCallActivity.class);
                    startActivity(next);

                } else if (flag == 1) {

                    Toast.makeText(getApplicationContext(),"Camera clicked",Toast.LENGTH_SHORT).show();

                } else {
                    Intent next = new Intent(MainActivity.this,AddContactActivity.class);
                    startActivity(next);
                }
            }
        });

        ai_and_addstatusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag>1){
                    Toast.makeText(getApplicationContext(),"ai btn clicked",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"write status clicked",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void loadfragment(Fragment fragment,int flag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(flag==0){
            ft.add(R.id.framelayout,fragment);
        }else {
            ft.replace(R.id.framelayout,fragment);
        }
        ft.commit();
    }


}