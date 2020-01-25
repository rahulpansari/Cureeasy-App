package com.example.cureeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressCustom;

public class SampleChat extends AppCompatActivity {
RecyclerView samplechatview;
SampleChatAdapter adapter;
ArrayList<DocType> doctype;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_chat);
        samplechatview=findViewById(R.id.samplechat);
        toolbar=findViewById(R.id.sampletool);
        setToolbar();

        setAdapter();
        setSupportActionBar(toolbar);


    }
    public ArrayList<DocType>getDocType()
    {
        ArrayList<DocType> doc=new ArrayList<>();
        String doctors[]={"Endocrinologist","Neurologist","Psychiatrist","Ophthalmologist","Urologist"};
        int images[]={R.drawable.endo,R.drawable.neurologist,R.drawable.psyc,R.drawable.optho,R.drawable.uro};
        for(int i=0;i<doctors.length;i++)
        {
          doc.add(new DocType(images[i],doctors[i])) ;
        }
        return doc;
    }

    public void setAdapter()
    {
        doctype=getDocType();
        adapter=new SampleChatAdapter(this,doctype);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        samplechatview.setLayoutManager(layoutManager);
        samplechatview.setAdapter(adapter);
    }

    public void setToolbar()
    {
        toolbar.setTitle("Select Type");
       toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
    }


}
