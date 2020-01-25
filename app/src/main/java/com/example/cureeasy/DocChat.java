package com.example.cureeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressCustom;

public class DocChat extends AppCompatActivity {

    RecyclerView doctorchatview;
    DoctorAdapter adapter;
    ACProgressCustom dialog;
    //ArrayList<DocType> doctype;
    FirebaseFunctions mFunctions;
    Toolbar toolbar;
    String getBundledata;
    DocChat.LoadData loaddata;
    ArrayList<Map<String, String>> results2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_chat);
        doctorchatview = findViewById(R.id.doctor_recycler);
        results2 = new ArrayList<>();
        toolbar = findViewById(R.id.doctortool);
        setToolbar();
        getBundledata = getIntentData();
        loaddata = new DocChat.LoadData();
        loaddata.execute(getBundledata);
        mFunctions=FirebaseFunctions.getInstance();
        /*getUser("HTYsvzthR9QuN7g2a0GDaKDZAw12")
                .addOnCompleteListener(new OnCompleteListener<ArrayList<Map<String, String>>>() {
                    @Override
                    public void onComplete(@NonNull Task<ArrayList<Map<String, String>>> task) {
                        // dismiss progress dialog
                        if (!task.isSuccessful()) {

                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }

                            // ...
                        }
                        //  work for msg
                        else {
                           // dismissProgress();
                            data = task.getResult();
                           // showRecycler(data);



                        }
                        // ...
                    }
                });*/
    }
    public void setToolbar() {
        toolbar.setTitle("Select User");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
    }
    public void setadapter() {
        //doctype=getDocType();
        adapter = new DoctorAdapter(this, results2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        doctorchatview.setLayoutManager(layoutManager);
        doctorchatview.setAdapter(adapter);
    }
    public String getIntentData() {
        SharedPreferences pred=getSharedPreferences("prev",MODE_PRIVATE);
        String s=pred.getString("userid",null);
        return s;
    }
    public void showProgress() {

        dialog = new ACProgressCustom.Builder(this)
                .useImages(R.drawable.endo, R.drawable.neurologist, R.drawable.optho, R.drawable.psyc).speed((float) 1.8)
                .build();
        dialog.setCancelable(false);
        dialog.show();


    }
    public void dismissProgress() {
        dialog.dismiss();
    }

    class LoadData extends AsyncTask<String, String, Task<ArrayList<Map<String, String>>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected void onPostExecute(Task< ArrayList<Map<String, String>>> mapTask) {
            super.onPostExecute(mapTask);
            mapTask
                    .addOnCompleteListener(new OnCompleteListener< ArrayList<Map<String, String>>>() {
                        @Override
                        public void onComplete(@NonNull Task<ArrayList<Map<String, String>>> task) {
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

                                Log.e("hi", task.getResult().toString());
                                setadapter();
                             /*   for (int i = 0; i < task.getResult().size(); i++) {
                                    results2.add(task.getResult().get(i));
                                }*/


                            }
                            // ...
                        }
                    });

        }
        @Override
        protected Task<ArrayList<Map<String, String>>> doInBackground(String... strings) {

            mFunctions = FirebaseFunctions.getInstance();
            return mFunctions
                    .getHttpsCallable("getchat")
                    .call(strings[0])
                    .continueWith(new Continuation<HttpsCallableResult,  ArrayList<Map<String, String>>>() {
                        @Override
                        public  ArrayList<Map<String, String>> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                            // This continuation runs on either success or failure, but if the task
                            // has failed then getResult() will throw an Exception which will be
                            // propagated down.
                            results2 = (ArrayList<Map<String, String>>) task.getResult().getData();
                            return results2;
                        }
                    });

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        loaddata.cancel(true);
    }
}
