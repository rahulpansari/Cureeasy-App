package com.example.cureeasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PatientHomeAdapter extends RecyclerView.Adapter<PatientHomeAdapter.PatientHomeHolder>
{ArrayList<PatientHome.PatientHomeDefault> homearr;
Context c;
    public PatientHomeAdapter(Context context, ArrayList<PatientHome.PatientHomeDefault> def) {
        super();
        homearr=def;
        c=context;
    }

    @NonNull
    @Override
    public PatientHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.patient_home_adapter,parent,false);
        return new PatientHomeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHomeHolder holder, int position) {
holder.img.setImageResource(homearr.get(position).image);
holder.text.setText(homearr.get(position).title);
holder.card.setCardBackgroundColor(c.getResources().getColor(homearr.get(position).col,c.getTheme()));
    }

    @Override
    public int getItemCount() {
        return homearr.size();
    }

    class PatientHomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView card;
        ImageView img;
        TextView text;
        public PatientHomeHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.sample_image);
            text=itemView.findViewById(R.id.sample_text);
            card=itemView.findViewById(R.id.cardp);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(getAdapterPosition()==1)
            {
                Intent i=new Intent(c,SampleChat.class);
                c.startActivity(i);
            }
            else if(getAdapterPosition()==0)
            {    SharedPreferences pref=c.getSharedPreferences("prev",MODE_PRIVATE);
                String id=pref.getString("userid",null);
                Intent i=new Intent(c,FetchPres.class);
                i.putExtra("userid",id);
                c.startActivity(i);
            }
            else if(getAdapterPosition()==2)
            {
                Intent i=new Intent(c,UserQr.class);
                c.startActivity(i);
            }

            else if(getAdapterPosition()==3)
            {
                Intent i=new Intent(c,MyProfile.class);
                c.startActivity(i);
            }
            else
            {
                Intent i=new Intent(c,Youtube.class);
                c.startActivity(i);
            }
        }
    }
}
