package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class contact_on_whatsapp_Adapter extends RecyclerView.Adapter<contact_on_whatsapp_Adapter.ViewHolder> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Context context;
    ArrayList<All_Users_Modal> arrayList;

    public contact_on_whatsapp_Adapter(Context context, ArrayList<All_Users_Modal> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contactlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        All_Users_Modal data = arrayList.get(position);

            holder.name.setText(data.name);
            holder.about.setText(data.about);
            holder.divider.setVisibility(View.INVISIBLE);
            Glide.with(context)
                    .load(data.imageurl)
                    .placeholder(R.drawable.userplaceeholder)
                    .into(holder.image);

        if (mAuth.getCurrentUser() == null || data.email == null){return;}

            String email = data.email.replace(".",",");
            String loginuseremail = mAuth.getCurrentUser().getEmail().replace(".",",");

            holder.adduser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database.getReference("AllUsers").child(loginuseremail)
                            .child("MyAllUsers").child(email)
                            .setValue(new My_All_chats_Modal(data.email,false,false,false))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(context,data.name + " added to chat",Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(context,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                           }
                        }
                    });

                }
            });

        database.getReference("AllUsers")
                .child(loginuseremail)
                .child("MyAllUsers")
                .child(email)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            holder.deleteuser.setVisibility(View.VISIBLE);
                            holder.adduser.setVisibility(View.GONE);
                        } else {
                            holder.deleteuser.setVisibility(View.GONE);
                            holder.adduser.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context,"Something went worng",Toast.LENGTH_SHORT).show();
                    }
                });



            holder.deleteuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.delete);
                    builder.setTitle("Remove User");
                    builder.setMessage("Do you want to sure for remove " + data.name + " from chat.");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            database.getReference("AllUsers").child(loginuseremail)
                                    .child("MyAllUsers").child(email).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(context,data.name + " removed from chat",Toast.LENGTH_SHORT).show();
                                                String sender_room = mAuth.getCurrentUser().getEmail().replace(".",",") + email;
                                                database.getReference().child("chats")
                                                        .child(sender_room)
                                                        .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                            }
                                                        });
                                            }else {
                                                Toast.makeText(context,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.create();
                    builder.show();


                }
            });

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.show_dp_dialog);
                    ImageView userdp = dialog.findViewById(R.id.dpimage);
                    TextView username = dialog.findViewById(R.id.username);

                    username.setText(arrayList.get(position).name);
                    Glide.with(context)
                            .load(arrayList.get(position).imageurl)
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
                            next.putExtra("name",arrayList.get(position).name);
                            next.putExtra("imageurl",arrayList.get(position).imageurl);
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
                            next.putExtra("name",arrayList.get(position).name);
                            next.putExtra("imageurl",arrayList.get(position).imageurl);
                            next.putExtra("email",data.email);
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
                            intent.putExtra("name", data.name);
                            intent.putExtra("imageurl", data.imageurl);
                            intent.putExtra("email", data.email);
                            context.startActivity(intent);
                        }
                    });

                    dialog.show();

                }
            });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image,deleteuser,adduser;
        TextView name,about;
        View divider;
        LinearLayout add_conatct_row;
        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.contactimage);
            name = itemView.findViewById(R.id.contactname);
            about = itemView.findViewById(R.id.conatcttagline);
            divider = itemView.findViewById(R.id.divider);
            add_conatct_row = itemView.findViewById(R.id.add_conatct_row);
            adduser = itemView.findViewById(R.id.adduser);
            deleteuser = itemView.findViewById(R.id.deleteuser);

        }
    }
}
