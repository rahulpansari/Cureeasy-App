package com.example.cureeasy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {
    Context context;
    ArrayList<Map<String, String>> firebasedata;

    public DoctorAdapter(Context x, ArrayList<Map<String, String>> adapter) {
        context = x;
        firebasedata = adapter;

    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View doctorrecycler = LayoutInflater.from(context).inflate(R.layout.sample_adapter_chat, parent, false);
        Log.e("hold","viewholder");
        return new DoctorHolder(doctorrecycler, firebasedata, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        holder.text.setText(firebasedata.get(position).get("name"));
        Log.e("hold","bind");
        Picasso.get().load(firebasedata.get(position).get("photourl")).placeholder(R.drawable.noimage).into(holder.profile);

    }

    @Override
    public int getItemCount() {
        return firebasedata.size();
    }

    class DoctorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ArrayList<Map<String, String>> data;
        CircleImageView profile;
        Context context;

        public DoctorHolder(@NonNull View itemView, ArrayList<Map<String, String>> arraydata, Context c) {
            super(itemView);
            text = itemView.findViewById(R.id.name);
            profile = itemView.findViewById(R.id.profile);
            data = arraydata;
            context = c;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, ChatActivity.class);
            Log.e("hold",getAdapterPosition()+"");
            i.putExtra("photourl",  firebasedata.get(getAdapterPosition()).get("photourl"));
            i.putExtra("userid",  firebasedata.get(getAdapterPosition()).get("id"));
            Log.e("tokkkkkkk",firebasedata.get(getAdapterPosition()).get("token"));
            i.putExtra("token",  firebasedata.get(getAdapterPosition()).get("token"));
            i.putExtra("name",  firebasedata.get(getAdapterPosition()).get("name"));
            context.startActivity(i);
        }
    }
}