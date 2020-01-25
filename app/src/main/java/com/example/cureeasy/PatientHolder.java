package com.example.cureeasy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PatientHolder extends RecyclerView.ViewHolder {

    TextView patient, disease, rect, prevt, apps, clinic, bill, doct, ins;
    public PatientHolder(@NonNull View itemView) {
        super(itemView);

        patient = (TextView) itemView.findViewById(R.id.patient);
        disease = (TextView) itemView.findViewById(R.id.disease);
        rect = (TextView) itemView.findViewById(R.id.rect);
        prevt = (TextView) itemView.findViewById(R.id.prevt);
        apps = (TextView) itemView.findViewById(R.id.apps);
        clinic = (TextView) itemView.findViewById(R.id.clinic);
        bill = (TextView) itemView.findViewById(R.id.bill);
        doct = (TextView) itemView.findViewById(R.id.doct);
        ins = (TextView) itemView.findViewById(R.id.ins);

    }
}
