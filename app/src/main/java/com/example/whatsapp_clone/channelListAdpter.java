package com.example.whatsapp_clone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class channelListAdpter extends RecyclerView.Adapter<channelListAdpter.ViewHolder> {

    Context context;
    ArrayList<channellistmodal> arrayList;

    public channelListAdpter(Context context,ArrayList<channellistmodal> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.channellistlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.channelname.setText(arrayList.get(position).channelname);
         holder.channelimage.setImageResource(arrayList.get(position).channelimage);
         holder.channelfollowers.setText(arrayList.get(position).channelfollowers);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView channelimage;
        TextView channelname,channelfollowers;

        Button channelfollowbtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            channelname = itemView.findViewById(R.id.channelname);
            channelimage = itemView.findViewById(R.id.channelimage);
            channelfollowers = itemView.findViewById(R.id.channelfollowers);
            channelfollowbtn = itemView.findViewById(R.id.channelfollowbtn);
        }
    }
}
