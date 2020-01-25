package com.example.cureeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PresAdapter extends RecyclerView.Adapter<PresAdapter.PresHolder> {
Context context;
ArrayList<MyObject> object;
    public PresAdapter(Context c, ArrayList<MyObject> obj) {
        super();
        context=c;
        object=obj;
    }

    @NonNull
    @Override
    public PresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.add_layout,parent,false);
        return new PresHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PresHolder holder, int position) {
holder.med.setText("Medicines "+object.get(position).name);
        holder.qty.setText("Quantity "+object.get(position).qty+"");
        holder.days.setText("Days "+object.get(position).days+"");
String time="";
            if(object.get(position).check[0]==1)
            {
                time=time+"BL ";

            }
            if(object.get(position).check[1]==1)
            {
                time=time+"AL ";
            }
            if(object.get(position).check[2]==1)
            {
                time=time+"BD ";
            }
            if(object.get(position).check[3]==1)
            {
                time=time+"AD";
            }
            holder.time.setText("Timings "+time);
        }




    @Override
    public int getItemCount() {
        return object.size();
    }

    class PresHolder extends RecyclerView.ViewHolder
 {
     TextView med,qty,days,time;
     public PresHolder(@NonNull View v) {
         super(v);
         med=v.findViewById(R.id.edit_medicine);
         qty=v.findViewById(R.id.edit_qty);
         days=v.findViewById(R.id.edit_days);
       time=v.findViewById(R.id.edit_time);
     }
 }
}
