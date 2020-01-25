package com.example.cureeasy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientDataAdapter extends RecyclerView.Adapter<PatientHolder> {

    Context c;
    ArrayList<PatientData> patientData;

    public PatientDataAdapter(Context c, ArrayList<PatientData> patientData){
        this.c = c;
        this.patientData = patientData;
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout

        View photoView = inflater.inflate(R.layout.row, parent, false);
        PatientHolder holder = new PatientHolder(photoView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        holder.patient.setText("Patient: " + patientData.get(position).Pname);
        holder.doct.setText("Diagnosed By: " + patientData.get(position).Dname);
        holder.clinic.setText("Diagnosed At: " + patientData.get(position).Cname);
        holder.disease.setText("Diagnostic Result: " + patientData.get(position).DisName);
        holder.apps.setText("Application Status: " + patientData.get(position).AppStatus);
        holder.rect.setText( "Recommended Treatment: " + patientData.get(position).RTname);
        holder.prevt.setText("Previous Treatment: " + patientData.get(position).PTname);
        holder.bill.setText("Billing Amount: " +  patientData.get(position).Bname);
        holder.ins.setText("Insurance Claim At: " + patientData.get(position).Ins);

    }

    @Override
    public int getItemCount() {
        return patientData.size();
    }
}
