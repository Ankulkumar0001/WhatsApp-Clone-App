package com.example.whatsapp_clone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class channelAdapter extends RecyclerView.Adapter<channelAdapter.ViewHolder> {

    Context context;
    ArrayList<All_Users_Modal> arrayList;

    public channelAdapter(Context context,ArrayList<All_Users_Modal> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatlayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).name);
        holder.lastmsg.setText(arrayList.get(position).lastmsg);
        holder.time.setText(arrayList.get(position).msgtime);
//        holder.imageView.setImageResource(arrayList.get(position).userimage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,lastmsg,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.userimage);
            name = itemView.findViewById(R.id.name);
            lastmsg = itemView.findViewById(R.id.lastmsg);
            time  = itemView.findViewById(R.id.chattime);
        }
    }

}
