package com.example.whatsapp_clone;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    Context context;
    ArrayList<statusmodal> arrayList;

    public StatusAdapter(Context context,ArrayList<statusmodal> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.statuslayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position == 0){
            holder.userphotocard.setVisibility(View.INVISIBLE);
            holder.statusimage.setVisibility(View.INVISIBLE);
            holder.addstatuscard.setVisibility(View.VISIBLE);

            int color = Color.parseColor("#000000");
            holder.statusname.setTextColor(color);
            holder.statusname.setText("Add Status");

        }else {

            holder.userphotocard.setVisibility(View.VISIBLE);
            holder.statusimage.setVisibility(View.VISIBLE);
            holder.addstatuscard.setVisibility(View.INVISIBLE);

            int color = Color.parseColor("#ffffff");
            holder.statusname.setTextColor(color);

            holder.statusname.setText(arrayList.get(position).statusname);
            holder.statusimage.setImageResource(arrayList.get(position).statusimage);
            holder.userphoto.setImageResource(arrayList.get(position).userphoto);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView statusimage,userphoto;
        TextView statusname;

        RelativeLayout addstatuscard;
        MaterialCardView userphotocard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusimage = itemView.findViewById(R.id.statusimage);
            userphoto = itemView.findViewById(R.id.userphoto);
            statusname = itemView.findViewById(R.id.statusname);
            addstatuscard = itemView.findViewById(R.id.addstatuscard);
            userphotocard = itemView.findViewById(R.id.userphotocard);
        }
    }
}
