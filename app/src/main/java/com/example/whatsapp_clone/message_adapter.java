package com.example.whatsapp_clone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class message_adapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<message_modal> arrayList;
    String reciever_email;
    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public message_adapter(Context context, ArrayList<message_modal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public message_adapter(Context context, ArrayList<message_modal> arrayList, String reciever_email) {
        this.context = context;
        this.arrayList = arrayList;
        this.reciever_email = reciever_email;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_chat_layout,parent,false);
            return new Sender_ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciever_chat_layout,parent,false);
            return new Reciever_ViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(arrayList.get(position).getEmail()
                .equals(mAuth.getCurrentUser()
                .getEmail())){
            return SENDER_VIEW_TYPE;
        }else {
            return RECIEVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        message_modal data = arrayList.get(position);
        if(holder.getClass() == Sender_ViewHolder.class){
            ((Sender_ViewHolder)holder).sender_chat.setText(data.getLastmsg());
            ((Sender_ViewHolder)holder).sender_chat_time.setText(formatTime(data.getMsgtime()));
        }else {
            ((Reciever_ViewHolder)holder).reciever_chat.setText(data.getLastmsg());
            ((Reciever_ViewHolder)holder).reciever_chat_time.setText(formatTime(data.getMsgtime()));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete message");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.bin);
                builder.setMessage("Are you sure to deleting this message ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sender_room = mAuth.getCurrentUser().getEmail().replace(".",",") + reciever_email.replace(".",",");
                        database.getReference().child("chats")
                                .child(sender_room)
                                .child(data.getMessageid())
                                .setValue(null);
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
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Sender_ViewHolder extends RecyclerView.ViewHolder{

        TextView sender_chat,sender_chat_time;
        ConstraintLayout chat;
        public Sender_ViewHolder(@NonNull View itemView) {
            super(itemView);
            sender_chat = itemView.findViewById(R.id.sender_chat_message);
            sender_chat_time = itemView.findViewById(R.id.sender_chat_time);
            chat = itemView.findViewById(R.id.sender_msg);
        }
    }

    public class Reciever_ViewHolder extends RecyclerView.ViewHolder{

        TextView reciever_chat,reciever_chat_time;
        public Reciever_ViewHolder(@NonNull View itemView) {
            super(itemView);
            reciever_chat = itemView.findViewById(R.id.reciever_chat_message);
            reciever_chat_time = itemView.findViewById(R.id.reciever_chat_time);
        }
    }


    public String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
}
