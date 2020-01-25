package com.example.cureeasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressCustom;

public class Prescription extends AppCompatActivity implements View.OnClickListener {
EditText pname,pemail,dname,presc,med,qty,days;
CheckBox blunch,alunch,bdinner,adinner;
TextView addmed;
    FirebaseFunctions mFunctions;
    ACProgressCustom dialog;
Button submit;
String drname;
RecyclerView presrecycler;
PresAdapter adapter;
MLoadData loadData;
Prescriptions load;
String name,email,token,id,presci;
ArrayList<MyObject> list;
String getBundleddata;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        pname=findViewById(R.id.edit_pname);
        pemail=findViewById(R.id.edit_pmail);
        dname=findViewById(R.id.edit_dname);
        presc=findViewById(R.id.edit_prescription);
        med=findViewById(R.id.edit_medicine);
        qty=findViewById(R.id.edit_qty);
        days=findViewById(R.id.edit_days);
        blunch=findViewById(R.id.cb_blunch);
        bdinner=findViewById(R.id.cb_bdinner);
        alunch=findViewById(R.id.cb_alunch);
        adinner=findViewById(R.id.cb_adinner);
        addmed=findViewById(R.id.add_medicine);
        submit=findViewById(R.id.add_pres_button);
        list=new ArrayList<>();
        presrecycler=findViewById(R.id.prescription_recycler);
        addmed.setOnClickListener(this);
        submit.setOnClickListener(this);
        adapter=new PresAdapter(this,list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        presrecycler.setLayoutManager(layoutManager);
        presrecycler.setAdapter(adapter);
        getBundleddata=getIntentData();
        getUser(getBundleddata);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_medicine)
        {boolean s=true;

            if(med.getText().toString().isEmpty())
            {
                med.setError("Medicine Name Cant be Empty");
                s=false;
            }
            if(qty.getText().toString().isEmpty())
            { s=false;
                qty.setError("Quantity Cant be Empty");
            }
            if(days.getText().toString().isEmpty())
            { s=false;
                days.setError("Days Name Cant be Empty");
            }
            if(!blunch.isChecked()&&!bdinner.isChecked()&&!alunch.isChecked()&&!adinner.isChecked())
            { s=false;
                blunch.setError("Select At Least One");
            }
if(s)
            {
            int check=0;
            int arr[]=new int[4];
            if(blunch.isChecked())
            {
                arr[check]=1;
                ++check;
            }
            else
            {
                arr[check]=0;
                ++check;
            }
            if(alunch.isChecked())
            {
                arr[check]=1;
                check++;
            }
            else
            {
                arr[check]=0;
                ++check;
            }
            if(bdinner.isChecked())
            {
                arr[check]=1;
                check++;
            }
            else
            {
                arr[check]=0;
                ++check;
            }
            if(adinner.isChecked())
            {
                arr[check]=1;
                check++;
            }
            else
            {
                arr[check]=0;
                ++check;
            }
            String medi=med.getText().toString();
            int qt=Integer.parseInt(qty.getText().toString());
            int d=Integer.parseInt(days.getText().toString());
            list.add(new MyObject(medi,qt,d,arr));
            adapter.notifyDataSetChanged();


        }}
        else
        {boolean s=true;
            if(presc.getText().toString().isEmpty())
            {
                presc.setError("Prescription Cant be Empty");
                s=false;
            }
            if(s)
            {
                String prescriptions=presc.getText().toString();
                String s2="";
                for(int i=0;i<list.size();i++)
                {String time="";
                    if(list.get(i).check[0]==1)
                    {
                        time=time+"BL";

                    }
                    if(list.get(i).check[1]==1)
                    {
                        time=time+"AL";
                    }
                    if(list.get(i).check[2]==1)
                    {
                        time=time+"BD";
                    }
                    if(list.get(i).check[3]==1)
                    {
                        time=time+"AD";
                    }
                    s2=s2+"Medicine:"+list.get(i).name+" Qty:"+list.get(i).qty+" Days:"+list.get(i).days+" Time:"+time+"\n";
                }
                Map<String,String> mmap=new HashMap<>();

                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                presci=presc.getText().toString();
                mmap.put("dname",drname);
                mmap.put("id",id);
                mmap.put("pres",presci);
                mmap.put("date",formattedDate);
                mmap.put("medicine",s2);
                mmap.put("token",token);
                setPrescription(mmap);
            }
        }
    }
    public void getUser(String bundle)
    {
        loadData = new Prescription.MLoadData();
        loadData.execute(bundle);
    }
    public String getIntentData() {
        Intent i = getIntent();
        String s = i.getStringExtra("userid");
        return s;
    }
    public void showProgress() {
        dialog = new ACProgressCustom.Builder(this)
                .useImages(R.drawable.endo, R.drawable.neurologist, R.drawable.optho, R.drawable.psyc).speed((float) 1.8)
                .build();
        dialog.setCancelable(false);
        dialog.show();


    }

    public void setPrescription(Map<String,String> pres)
    {
        load = new Prescription.Prescriptions();
        load.execute(pres);
    }
    public void dismissProgress() {
        dialog.dismiss();
    }
        class MLoadData extends AsyncTask<String, String, Task<Map<String, String>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected void onPostExecute(Task<Map<String, String>> mapTask) {
            super.onPostExecute(mapTask);
            mapTask
                    .addOnCompleteListener(new OnCompleteListener<Map<String, String>>() {
                        @Override
                        public void onComplete(@NonNull Task<Map<String, String>> task) {
                            dismissProgress();
                            // dismiss progress dialog
                            if (!task.isSuccessful()) {

                                Exception e = task.getException();
                                if (e instanceof FirebaseFunctionsException) {
                                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                    FirebaseFunctionsException.Code code = ffe.getCode();
                                    Object details = ffe.getDetails();
                                }

                                // ...
                            } else {
name=task.getResult().get("name");
email=task.getResult().get("email");
token=task.getResult().get("token");
id=task.getResult().get("id");
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("prev", MODE_PRIVATE);
                                drname=pref.getString("username",null);

pname.setText(name);
dname.setText(drname);
pemail.setText(email);
                              //  Log.e("hi", task.getResult().get("users").toString());



                            }
                            // ...
                        }
                    });

        }

        @Override
        protected Task<Map<String, String>> doInBackground(String... strings) {
            Map<String, Object> data = new HashMap<>();

            data.put("id", strings[0]);
            mFunctions = FirebaseFunctions.getInstance();
            return mFunctions
                    .getHttpsCallable("getuser")
                    .call(data)
                    .continueWith(new Continuation<HttpsCallableResult, Map<String, String>>() {
                        @Override
                        public Map<String, String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                            // This continuation runs on either success or failure, but if the task
                            // has failed then getResult() will throw an Exception which will be
                            // propagated down.
                            return (Map<String, String>) task.getResult().getData();

                        }
                    });

        }
    }

    class Prescriptions extends AsyncTask<Map<String,String>,Void,Task<Map<String, String>>>
    {
        @Override
        protected Task<Map<String, String>> doInBackground(Map<String, String>... maps) {
            return mFunctions
                    .getHttpsCallable("setprescription")
                    .call(maps[0])
                    .continueWith(new Continuation<HttpsCallableResult, Map<String, String>>() {
                        @Override
                        public Map<String, String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                            // This continuation runs on either success or failure, but if the task
                            // has failed then getResult() will throw an Exception which will be
                            // propagated down.
                            return (Map<String, String>) task.getResult().getData();

                        }
                    });

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected void onPostExecute(Task<Map<String, String>> mapTask) {

            super.onPostExecute(mapTask);
            mapTask
                    .addOnCompleteListener(new OnCompleteListener<Map<String, String>>() {
                        @Override
                        public void onComplete(@NonNull Task<Map<String, String>> task) {
                            dismissProgress();
                            // dismiss progress dialog
                            if (!task.isSuccessful()) {

                                Exception e = task.getException();
                                if (e instanceof FirebaseFunctionsException) {
                                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                    FirebaseFunctionsException.Code code = ffe.getCode();
                                    Object details = ffe.getDetails();
                                }

                                // ...
                            } else {

                                    finish();

                            }
                            // ...
                        }
                    });

        }
    }
}
class MyObject
{
    String name;
    int qty;
    int days;
    int check[];
    public MyObject(String n,int q,int d,int c[])
    {
        name=n;
        qty=q;
        days=d;
        check=c;
    }

}
