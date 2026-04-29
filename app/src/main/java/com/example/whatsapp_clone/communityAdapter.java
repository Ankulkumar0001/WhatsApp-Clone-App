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

public class communityAdapter extends RecyclerView.Adapter<communityAdapter.ViewHolder> {

    Context context;
    ArrayList<communitymodal> arrayList;

    public communityAdapter(Context context,ArrayList<communitymodal> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.communitylayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.communityname.setText(arrayList.get(position).communityname);
        holder.communitylastmsg.setText(arrayList.get(position).communitylastmsg);
        holder.communityimage.setImageResource(arrayList.get(position).communityimage);
        holder.communnitychattime.setText(arrayList.get(position).communitychattime);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView communityimage;
        TextView communityname,communitylastmsg,communnitychattime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            communityname = itemView.findViewById(R.id.communityname);
            communityimage = itemView.findViewById(R.id.communityimage);
            communitylastmsg = itemView.findViewById(R.id.communitylastmsg);
            communnitychattime = itemView.findViewById(R.id.communitychattime);
        }
    }
}
