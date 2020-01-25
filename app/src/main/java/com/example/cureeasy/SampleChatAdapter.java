package com.example.cureeasy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SampleChatAdapter extends RecyclerView.Adapter<SampleChatHolder>{
    Context context;
    ArrayList<DocType>arrayList;
    public SampleChatAdapter(Context x, ArrayList<DocType>a) {
        super();
        context=x;
        arrayList=a;
    }

    @NonNull
    @Override
    public SampleChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyleview=LayoutInflater.from(context).inflate(R.layout.sample_adapter_chat,parent,false);
        return new SampleChatHolder(recyleview,arrayList,context);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleChatHolder holder, int position) {
holder.text.setText(arrayList.get(position).name);
holder.profile.setImageResource(arrayList.get(position).image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
 class SampleChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView text;
    CircleImageView profile;
    ArrayList<DocType>docType;
    Context context;
    public SampleChatHolder(@NonNull View itemView,ArrayList<DocType> type,Context x) {
        super(itemView);
        text=itemView.findViewById(R.id.name);
        profile=itemView.findViewById(R.id.profile);
        docType=type;
        context=x;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context,docType.get(getAdapterPosition()).name,Toast.LENGTH_SHORT).show();
        Intent i=new Intent(context,DoctorActivity.class);
        i.putExtra("doctortype",docType.get(getAdapterPosition()).name);
        context.startActivity(i);
    }
}
