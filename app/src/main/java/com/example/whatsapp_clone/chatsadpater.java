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
import androidx.cardview.widget.CardView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class chatsadpater extends RecyclerView.Adapter<chatsadpater.ViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context context;
    ArrayList<My_All_chats_Modal> arrayList;


    public chatsadpater(Context context, ArrayList<My_All_chats_Modal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.setIsRecyclable(false);

        database.getReference("AllUsers").child(arrayList.get(position).email.replace(".", ",")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String imageurl, name, lastmsg, msgtime, email;
                imageurl = snapshot.child("imageurl").getValue(String.class);
                name = snapshot.child("name").getValue(String.class);
                lastmsg = snapshot.child("lastmsg").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);


                holder.name.setText(name);
                Glide.with(context)
                        .load(imageurl)
                        .placeholder(R.drawable.userplaceeholder)
                        .into(holder.imageView);


                String senderemail = mAuth.getCurrentUser().getEmail().replace(".", ",");
                String recieveremail = arrayList.get(position).email.replace(".", ",");
                String senderroom = senderemail + recieveremail;

                database.getReference("AllUsers")
                        .child(senderemail)
                        .child("MyAllUsers")
                        .child(recieveremail).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    holder.ismute = snapshot.child("ismute").getValue(Boolean.class);
                                    holder.ispined = snapshot.child("ispined").getValue(Boolean.class);
                                    holder.isblock = snapshot.child("isblock").getValue(Boolean.class);
                                }

                                if (holder.ispined == null && holder.ismute == null && holder.isblock == null) {
                                    return;
                                }

                                if (holder.ismute) {
                                    holder.mutenotificationbtn.setVisibility(View.VISIBLE);
                                } else {
                                    holder.mutenotificationbtn.setVisibility(View.GONE);
                                }

                                if (holder.ispined) {
                                    holder.pinchatbtn.setVisibility(View.VISIBLE);
                                } else {
                                    holder.pinchatbtn.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                database.getReference().child("chats")
                        .child(senderroom)
                        .orderByChild("msgtime")
                        .limitToLast(1)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChildren()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String msg = dataSnapshot.child("lastmsg").getValue(String.class);
                                        Long timestamp = dataSnapshot.child("msgtime").getValue(Long.class);

                                        if(holder.isblock){
                                            holder.lastmsg.setText("🚫 You block this person.");
                                        }else {
                                            holder.lastmsg.setText(msg);
                                            if (timestamp != null) {
                                                holder.time.setText(formatTime(timestamp));
                                            }
                                        }

                                    }
                                } else {

                                    if(holder.isblock) {
                                        holder.lastmsg.setText("🚫 You block this person.");

                                    }else {
                                        holder.lastmsg.setText(lastmsg);
                                        holder.time.setText("");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                holder.chatlayoutrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent next = new Intent(context, ChatwithUserActivity.class);
                        next.putExtra("name", name);
                        next.putExtra("imageurl", imageurl);
                        next.putExtra("email", email);
                        context.startActivity(next);
                    }
                });

                holder.chatlayoutrow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.option_dialog_box);
                        LinearLayout mutenoti, unmutenoti, deletechat, pinchat, unpinchat, viewprofile, clearchat, block,unblock;
                        mutenoti = dialog.findViewById(R.id.mutenotification);
                        deletechat = dialog.findViewById(R.id.deletechat);
                        pinchat = dialog.findViewById(R.id.pinchat);
                        viewprofile = dialog.findViewById(R.id.viewprofile);
                        clearchat = dialog.findViewById(R.id.clearchat);
                        block = dialog.findViewById(R.id.block);
                        unmutenoti = dialog.findViewById(R.id.unmutenotification);
                        unpinchat = dialog.findViewById(R.id.unpinchat);
                        unblock = dialog.findViewById(R.id.unblock);


                        if (holder.ismute) {
                            mutenoti.setVisibility(View.GONE);
                            unmutenoti.setVisibility(View.VISIBLE);
                        } else {
                            mutenoti.setVisibility(View.VISIBLE);
                            unmutenoti.setVisibility(View.GONE);
                        }

                        if (holder.ispined) {
                            pinchat.setVisibility(View.GONE);
                            unpinchat.setVisibility(View.VISIBLE);
                        } else {
                            pinchat.setVisibility(View.VISIBLE);
                            unpinchat.setVisibility(View.GONE);
                        }

                        if (holder.isblock) {
                            block.setVisibility(View.GONE);
                            unblock.setVisibility(View.VISIBLE);

                        } else {
                            block.setVisibility(View.VISIBLE);
                            unblock.setVisibility(View.GONE);
                        }

                        viewprofile.setOnClickListener(new View.OnClickListener() {
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

                        mutenoti.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("ismute", true);
                                database.getReference("AllUsers")
                                        .child(senderemail)
                                        .child("MyAllUsers")
                                        .child(recieveremail)
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });

                        unmutenoti.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("ismute", false);
                                database.getReference("AllUsers")
                                        .child(senderemail)
                                        .child("MyAllUsers")
                                        .child(recieveremail)
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });

                        deletechat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Delete chat");
                                builder.setCancelable(false);
                                builder.setIcon(R.drawable.bin);
                                builder.setMessage("Are you sure you want to delete this chat? All messages will be removed.");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String sender_room = mAuth.getCurrentUser().getEmail().replace(".",",") + recieveremail;
                                        database.getReference().child("chats")
                                                .child(sender_room)
                                                .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            database.getReference("AllUsers")
                                                                    .child(senderemail)
                                                                    .child("MyAllUsers")
                                                                    .child(recieveremail)
                                                                    .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(context,name + " Deleted from chats",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

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

                        pinchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("ispined", true);
                                database.getReference("AllUsers")
                                        .child(senderemail)
                                        .child("MyAllUsers")
                                        .child(recieveremail)
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });

                        unpinchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("ispined", false);
                                database.getReference("AllUsers")
                                        .child(senderemail)
                                        .child("MyAllUsers")
                                        .child(recieveremail)
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });

                        clearchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Clear chat");
                                builder.setCancelable(false);
                                builder.setIcon(R.drawable.clearchat);
                                builder.setMessage("Are you sure to clear chat?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String sender_room = mAuth.getCurrentUser().getEmail().replace(".",",") + recieveremail;
                                        database.getReference().child("chats")
                                                .child(sender_room)
                                                .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()) {
                                                                if (holder.isblock) {
                                                                    holder.lastmsg.setText("🚫 You block this person.");
                                                                    holder.time.setText("");
                                                                } else{
                                                                    holder.lastmsg.setText(lastmsg);
                                                                    holder.time.setText("");
                                                                }
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

                        block.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("isblock", true);
                                database.getReference("AllUsers")
                                        .child(senderemail)
                                        .child("MyAllUsers")
                                        .child(recieveremail)
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                            }
                                        });
                            }
                        });

                        unblock.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("isblock", false);
                                database.getReference("AllUsers")
                                        .child(senderemail)
                                        .child("MyAllUsers")
                                        .child(recieveremail)
                                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });


                        dialog.show();
                        return true;
                    }
                });

                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.show_dp_dialog);

                        TextView username = dialog.findViewById(R.id.username);
                        username.setText(name);

                        ImageView image = dialog.findViewById(R.id.dpimage);

                        Glide.with(context)
                                .load(imageurl)
                                .placeholder(R.drawable.userplaceeholder)
                                .into(image);

                        LinearLayout chat_with_user, voicecall_with_user, videocall_with_user, info_of_user;
                        ConstraintLayout dialog_dp = dialog.findViewById(R.id.dialog_dp);

                        chat_with_user = dialog.findViewById(R.id.message_with_user);
                        voicecall_with_user = dialog.findViewById(R.id.voicecall_with_user);
                        videocall_with_user = dialog.findViewById(R.id.videocall_with_user);
                        info_of_user = dialog.findViewById(R.id.info_of_user);

                        dialog_dp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent next = new Intent(context, Show_Full_User_DP_Activity.class);
                                next.putExtra("name", name);
                                next.putExtra("imageurl", imageurl);
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
                                Intent next = new Intent(context, ChatwithUserActivity.class);
                                next.putExtra("name", name);
                                next.putExtra("imageurl", imageurl);
                                next.putExtra("email", email);
                                context.startActivity(next);

                            }
                        });

                        voicecall_with_user.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "voice call clicked", Toast.LENGTH_SHORT).show();
                            }
                        });

                        videocall_with_user.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "video call clicked", Toast.LENGTH_SHORT).show();
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Boolean ismute = false;
        Boolean ispined = false;
        Boolean isblock = false;

        ImageView imageView, mutenotificationbtn, pinchatbtn;
        TextView name, lastmsg, time;

        LinearLayout enterinchat, chatlayoutrow;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userimage);
            name = itemView.findViewById(R.id.name);
            lastmsg = itemView.findViewById(R.id.lastmsg);
            time = itemView.findViewById(R.id.chattime);
            enterinchat = itemView.findViewById(R.id.entertochat);
            card = itemView.findViewById(R.id.card);
            chatlayoutrow = itemView.findViewById(R.id.chatlayoutrow);
            mutenotificationbtn = itemView.findViewById(R.id.mutenotificationbtn);
            pinchatbtn = itemView.findViewById(R.id.pinchatbtn);
        }
    }

    public String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

}
