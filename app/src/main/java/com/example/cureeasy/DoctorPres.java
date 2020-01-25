package com.example.cureeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class DoctorPres extends AppCompatActivity {
RecyclerView drpres;
String id;
    ArrayList<DoctorPres.DoctorHomeDefault> slider_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_pres);
        drpres=findViewById(R.id.dr_pres_rec);
        slider_adapter=declarepatient();
        Intent i=getIntent();
        id=i.getStringExtra("userid");
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        DoctorPresAdapter homeadapter=new DoctorPresAdapter(this,slider_adapter,id);

        drpres.setLayoutManager(manager);
        drpres.setAdapter(homeadapter);
    }
    public ArrayList<DoctorPres.DoctorHomeDefault>declarepatient()
    {
        ArrayList<DoctorPres.DoctorHomeDefault> def=new ArrayList<>();
        //def.add(new DoctorHome.DoctorHomeDefault("Prescription",R.drawable.prescription_icon_img,R.color.tile1));
        def.add(new DoctorPres.DoctorHomeDefault("Upload Prescription",R.drawable.prescription_icon_img,R.color.tile3));
        def.add(new DoctorPres.DoctorHomeDefault("Verify Prescription",R.drawable.verify,R.color.tile2));

        return def;
    }
    class DoctorHomeDefault
    {
        String title;
        int image;
        int col;
        public DoctorHomeDefault(String t,int i,int c)
        {
            title=t;
            image=i;
            col=c;
        }
    }
}
