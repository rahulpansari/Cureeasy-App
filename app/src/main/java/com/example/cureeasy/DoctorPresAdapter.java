package com.example.cureeasy;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorPresAdapter extends RecyclerView.Adapter<DoctorPresAdapter.DoctorPresHolder>
{
    Context c;
    String i1;
    ArrayList<DoctorPres.DoctorHomeDefault> slider_adapter;
    public DoctorPresAdapter(Context context,ArrayList<DoctorPres.DoctorHomeDefault> f,String id) {
        super();
        c=context;
        slider_adapter=f;
        i1=id;

    }

    @NonNull
    @Override
    public DoctorPresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.patient_home_adapter,parent,false);
        return new DoctorPresHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorPresHolder holder, int position) {
        holder.img.setImageResource(slider_adapter.get(position).image);
        holder.text.setText(slider_adapter.get(position).title);
        holder.card.setCardBackgroundColor(c.getResources().getColor(slider_adapter.get(position).col,c.getTheme()));
    }

    @Override
    public int getItemCount() {
        return slider_adapter.size();
    }

    class DoctorPresHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    CardView card;
    ImageView img;
    TextView text;
    public DoctorPresHolder(@NonNull View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.sample_image);
        text=itemView.findViewById(R.id.sample_text);
        card=itemView.findViewById(R.id.cardp);
        card.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(getAdapterPosition()==0)
        { Intent i=new Intent(c,Prescription.class);
        i.putExtra("userid",i1);
            c.startActivity(i);

        }
        else if(getAdapterPosition()==1)
        {
            Intent i=new Intent(c,FetchPres.class);
            i.putExtra("userid",i1);
            c.startActivity(i);
        }

    }
}
}
