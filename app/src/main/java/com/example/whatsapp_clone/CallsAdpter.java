package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CallsAdpter extends RecyclerView.Adapter<CallsAdpter.ViewHolder> {

    Context context;
    ArrayList<callslogmodal> arrayList;

    public CallsAdpter(Context context,ArrayList<callslogmodal> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.callslayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.callsname.setText(arrayList.get(position).callsusername);
        holder.callstime.setText(arrayList.get(position).callstime);
        holder.calluserimage.setImageResource(arrayList.get(position).calluserimage);

        holder.calluserimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.show_dp_dialog);
                ImageView image = dialog.findViewById(R.id.dpimage);
                TextView name = dialog.findViewById(R.id.username);

                LinearLayout chat_with_user,voicecall_with_user,videocall_with_user,info_of_user;

                image.setImageResource(arrayList.get(position).calluserimage);
                name.setText(arrayList.get(position).callsusername);

                dialog.show();

               chat_with_user = dialog.findViewById(R.id.message_with_user);
               voicecall_with_user = dialog.findViewById(R.id.voicecall_with_user);
               videocall_with_user = dialog.findViewById(R.id.videocall_with_user);
               info_of_user = dialog.findViewById(R.id.info_of_user);

               chat_with_user.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent next = new Intent(context,ChatwithUserActivity.class);
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
                       Toast.makeText(context,"info of user clicked",Toast.LENGTH_SHORT).show();
                   }
               });

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView calluserimage;
        TextView callsname,callstime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            callsname = itemView.findViewById(R.id.callsname);
            callstime = itemView.findViewById(R.id.calltime);
            calluserimage = itemView.findViewById(R.id.calluserimage);

        }
    }
}
