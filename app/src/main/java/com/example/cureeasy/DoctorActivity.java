package com.example.cureeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class DoctorActivity extends AppCompatActivity {
    RecyclerView doctorchatview;
    DoctorAdapter adapter;
    ACProgressCustom dialog;
    //ArrayList<DocType> doctype;
    FirebaseFunctions mFunctions;
    Toolbar toolbar;
    String getBundledata;
    LoadData loaddata;
    ArrayList<Map<String, String>> results2;

    Map<String, ArrayList<Map<String, String>>> result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        doctorchatview = findViewById(R.id.doctor_recycler);
        results2 = new ArrayList<>();

        setadapter();
        toolbar = findViewById(R.id.doctortool);
        setToolbar();
        getBundledata = getIntentData();
        loaddata = new LoadData();
        loaddata.execute(getBundledata);


    }


    public void setToolbar() {
        toolbar.setTitle("Select Doctor");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
    }


    // setadapter for recycler view
    public void setadapter() {
        //doctype=getDocType();
        adapter = new DoctorAdapter(this, results2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        doctorchatview.setLayoutManager(layoutManager);
        doctorchatview.setAdapter(adapter);
    }

    public String getIntentData() {
        Intent i = getIntent();
        String s = i.getStringExtra("doctortype");
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

    class LoadData extends AsyncTask<String, String, Task<Map<String, ArrayList<Map<String, String>>>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected void onPostExecute(Task<Map<String, ArrayList<Map<String, String>>>> mapTask) {
            super.onPostExecute(mapTask);
            mapTask
                    .addOnCompleteListener(new OnCompleteListener<Map<String, ArrayList<Map<String, String>>>>() {
                        @Override
                        public void onComplete(@NonNull Task<Map<String, ArrayList<Map<String, String>>>> task) {
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

                                Log.e("hi", task.getResult().get("users").toString());
                                for (int i = 0; i < task.getResult().get("users").size(); i++) {
                                    results2.add(task.getResult().get("users").get(i));
                                }

                                adapter.notifyDataSetChanged();
                            }
                            // ...
                        }
                    });

        }

        @Override
        protected Task<Map<String, ArrayList<Map<String, String>>>> doInBackground(String... strings) {
            Map<String, Object> data = new HashMap<>();

            data.put("type", strings[0]);
            mFunctions = FirebaseFunctions.getInstance();
            return mFunctions
                    .getHttpsCallable("getDoctors")
                    .call(data)
                    .continueWith(new Continuation<HttpsCallableResult, Map<String, ArrayList<Map<String, String>>>>() {
                        @Override
                        public Map<String, ArrayList<Map<String, String>>> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                            // This continuation runs on either success or failure, but if the task
                            // has failed then getResult() will throw an Exception which will be
                            // propagated down.
                            result2 = (Map<String, ArrayList<Map<String, String>>>) task.getResult().getData();
                            return result2;
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
