package com.example.whatsapp_clone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class invitecontactAdapter extends RecyclerView.Adapter<invitecontactAdapter.ViewHolder> {

    Context context;
    ArrayList<invitecontactmodal> arrayList;

    int flag;

    public invitecontactAdapter(Context context,ArrayList<invitecontactmodal> arrayList,int flag){
        this.context = context;
        this.arrayList = arrayList;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invitecontactlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(flag == 0){
            holder.contactname.setText(arrayList.get(position).contactname);
            holder.contactmobilenumber.setText(arrayList.get(position).contactmobilenumber);
            holder.dividerforinvite.setVisibility(View.VISIBLE);

        }else {
            holder.contactname.setText(arrayList.get(position).contactname);
            holder.contactmobilenumber.setText(arrayList.get(position).contactmobilenumber);
            holder.dividerforinvite.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView contactname,contactmobilenumber;
        View dividerforinvite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactname = itemView.findViewById(R.id.invitecontactname);
            contactmobilenumber = itemView.findViewById(R.id.contactmobilenumber);
            dividerforinvite = itemView.findViewById(R.id.dividerforinvite);

        }
    }
}
