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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressCustom;

public class FetchPres extends AppCompatActivity {
    FirebaseFunctions mFunctions;
    String id;
    ACProgressCustom dialog;
    ArrayList<Map<String, String>> data;
    RecyclerView fetchrecycler;
    Toolbar prestoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_pres);
        fetchrecycler=findViewById(R.id.recycler_pres);
        data=new ArrayList<>();
        prestoolbar=findViewById(R.id.pres_toolbar);
        prestoolbar.setTitleTextColor(Color.WHITE);
        prestoolbar.setTitle("Prescription");
        setSupportActionBar(prestoolbar);
        mFunctions=FirebaseFunctions.getInstance();
        Intent i=getIntent();
        id=i.getStringExtra("userid");
       // SharedPreferences pref=getSharedPreferences("prev",MODE_PRIVATE);
        //id=pref.getString("userid",null);
        new AsyncWork().execute(id);

    }
    private Task<ArrayList<Map<String, String>>> getUser(String userid) {
        // Take from Intent.

        Map<String,String>uid=new HashMap<>();
        uid.put("id",userid);



        return mFunctions
                .getHttpsCallable("showreports")
                .call(uid)
                .continueWith(new Continuation<HttpsCallableResult, ArrayList<Map<String, String>>>() {
                    @Override
                    public ArrayList<Map<String, String>> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        data= (ArrayList<Map<String, String>>) task.getResult().getData();
                        return data;
                    }
                });
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
    class AsyncWork extends AsyncTask<String,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {
            getUser(strings[0])
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
                                dismissProgress();
                                data = task.getResult();
                                showRecycler(data);



                            }
                            // ...
                        }
                    });
            return null;
        }
    }

    public void showRecycler(ArrayList<Map<String,String>> d)
    {
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        FetchPresAdapter adapter=new FetchPresAdapter(getApplicationContext(),d);
        fetchrecycler.setLayoutManager(manager);
        fetchrecycler.setAdapter(adapter);
    }
}
