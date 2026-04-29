package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class frequentlycontactAdpter extends RecyclerView.Adapter<frequentlycontactAdpter.ViewHolder> {

    Context context;
    int flag;
    ArrayList<My_All_chats_Modal> arrayList;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public frequentlycontactAdpter(Context context, ArrayList<My_All_chats_Modal> arrayList , int flag){
        this.context = context;
        this.arrayList = arrayList;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contactlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        database.getReference("AllUsers").child(arrayList.get(position).email.replace(".",",")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String imageurl,name,about,email;
                    imageurl = snapshot.child("imageurl").getValue(String.class);
                    name = snapshot.child("name").getValue(String.class);
                    about = snapshot.child("about").getValue(String.class);
                    email = snapshot.child("email").getValue(String.class);


                    holder.name.setText(name);
                    holder.tagline.setText(about);
                    Glide.with(context)
                            .load(imageurl)
                            .placeholder(R.drawable.userplaceeholder)
                            .into(holder.image);

                    if(flag == 0){
                        holder.divider.setVisibility(View.VISIBLE);
                    }else {
                        holder.divider.setVisibility(View.GONE);
                        holder.add_conatct_row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent next = new Intent(context,ChatwithUserActivity.class);
                                next.putExtra("name",name);
                                next.putExtra("imageurl",imageurl);
                                next.putExtra("email",email);
                                context.startActivity(next);
                            }
                        });
                    }

                    holder.delete_user.setVisibility(View.GONE);
                    holder.adduser.setVisibility(View.GONE);

                    holder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.show_dp_dialog);
                            ImageView userdp = dialog.findViewById(R.id.dpimage);
                            TextView username = dialog.findViewById(R.id.username);

                            username.setText(name);
                            Glide.with(context)
                                    .load(imageurl)
                                    .placeholder(R.drawable.userplaceeholder)
                                    .into(userdp);

                            LinearLayout chat_with_user,voicecall_with_user,videocall_with_user,info_of_user;
                            ConstraintLayout dialog_dp = dialog.findViewById(R.id.dialog_dp);

                            chat_with_user = dialog.findViewById(R.id.message_with_user);
                            voicecall_with_user = dialog.findViewById(R.id.voicecall_with_user);
                            videocall_with_user = dialog.findViewById(R.id.videocall_with_user);
                            info_of_user = dialog.findViewById(R.id.info_of_user);

                            dialog_dp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent next = new Intent(context,Show_Full_User_DP_Activity.class);
                                    next.putExtra("name",name);
                                    next.putExtra("imageurl",imageurl);
                                    context.startActivity(next);
                                    if (context instanceof Activity) {
                                        ((Activity) context).overridePendingTransition(R.anim.anim, R.anim.second);
                                    }
                                }
                            });

                            chat_with_user.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    Intent next = new Intent(context,ChatwithUserActivity.class);
                                    next.putExtra("name",name);
                                    next.putExtra("imageurl",imageurl);
                                    next.putExtra("email",email);
                                    context.startActivity(next);
                                }
                            });

                            voicecall_with_user.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(context,"voice call clicked",Toast.LENGTH_SHORT).show();
                                }
                            });

                            videocall_with_user.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(context,"video call clicked",Toast.LENGTH_SHORT).show();
                                }
                            });

                            info_of_user.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(context, View_User_Profile.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("imageurl", imageurl);
                                    intent.putExtra("email", email);
                                    context.startActivity(intent);
                                }
                            });

                            dialog.show();

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList != null){
            return Math.min(arrayList.size(),5);
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image,adduser,delete_user;
        TextView name,tagline;
        LinearLayout add_conatct_row;
        View divider;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.contactimage);
            name = itemView.findViewById(R.id.contactname);
            tagline = itemView.findViewById(R.id.conatcttagline);
            divider = itemView.findViewById(R.id.divider);
            add_conatct_row = itemView.findViewById(R.id.add_conatct_row);
            adduser = itemView.findViewById(R.id.adduser);
            delete_user = itemView.findViewById(R.id.deleteuser);
        }
    }
}
