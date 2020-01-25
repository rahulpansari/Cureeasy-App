package com.example.cureeasy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DoctorHomeAdapter extends RecyclerView.Adapter<DoctorHomeAdapter.DoctorHomeHolder>  {
    ArrayList<DoctorHome.DoctorHomeDefault> homearr;
    Context c;
    Activity a;
    public DoctorHomeAdapter(Context context, ArrayList<DoctorHome.DoctorHomeDefault> def,Activity ac) {
        super();
        homearr=def;
        c=context;
        a=ac;
    }


    @NonNull
    @Override
    public DoctorHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.patient_home_adapter,parent,false);
        return new DoctorHomeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHomeHolder holder, int position) {
        holder.img.setImageResource(homearr.get(position).image);
        holder.text.setText(homearr.get(position).title);
        holder.card.setCardBackgroundColor(c.getResources().getColor(homearr.get(position).col,c.getTheme()));

    }

    @Override
    public int getItemCount() {
        return homearr.size();
    }

    class DoctorHomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {   private int camera_permission=101;
        CardView card;
        ImageView img;
        TextView text;
        public DoctorHomeHolder(@NonNull View itemView) {
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
                if (ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent i=new Intent(c,BarCodeScanningActivity.class);
                    c.startActivity(i);
                }
                else
                {
                    askPermission();
                }


            }
            else if(getAdapterPosition()==0)
            {
                Intent i=new Intent(c,DocChat.class);
                c.startActivity(i);
            }
            else if(getAdapterPosition()==2)
            {
                Intent i=new Intent(c,MyProfile.class);
                c.startActivity(i);
            }
            else if(getAdapterPosition()==3)
            {
                Intent i=new Intent(c,Youtube.class);
                c.startActivity(i);
            }

        }
        public void askPermission()
        {
            if (ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(a,
                        new String[]{Manifest.permission.CAMERA},
                        camera_permission);


            }
        }


    }


}
