package com.example.cureeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class FetchPresAdapter  extends RecyclerView.Adapter<FetchPresAdapter.FetchPresHolder> {
    Context context;
    ArrayList<Map<String,String>> arr;
    public FetchPresAdapter(Context c,ArrayList<Map<String,String>> a) {
        super();
        context=c;
        arr=a;
    }

    @NonNull
    @Override
    public FetchPresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.pres_layout,parent,false);
        return new FetchPresHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FetchPresHolder holder, int position) {
    holder.drname.setText(arr.get(position).get("dname"));
        holder.date.setText(arr.get(position).get("date"));
        holder.med.setText(arr.get(position).get("medicine"));
        holder.pres.setText(arr.get(position).get("pres"));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    class FetchPresHolder extends RecyclerView.ViewHolder
{
    TextView drname,pres,date,med;
    public FetchPresHolder(@NonNull View itemView) {
        super(itemView);
        drname=itemView.findViewById(R.id.dr_name);
        pres=itemView.findViewById(R.id.dr_pres);
        date=itemView.findViewById(R.id.dr_date);
        med=itemView.findViewById(R.id.dr_med);
    }
}
}
