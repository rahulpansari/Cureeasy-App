package com.example.cureeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressCustom;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {
Toolbar toolbar;
    Map<String, String> data;
CircleImageView profileimg;
    ACProgressCustom dialog;
EditText mname,email,mobile;
FirebaseFunctions mFunctions;
Button button;
String m[];
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = new HashMap<>();
        m=new String[3];
        profileimg=findViewById(R.id.mprofileimg);
        mname=findViewById(R.id.medit_pname);
        email=findViewById(R.id.medit_pmail);
        mobile=findViewById(R.id.medit_pmobile);
        button=findViewById(R.id.button);
        button.setOnClickListener(this);
mFunctions=FirebaseFunctions.getInstance();
        SharedPreferences pref=getSharedPreferences("prev",MODE_PRIVATE);
        m[0]="user";
        id=pref.getString("userid",null);
        m[1]=id;
        new AsyncWork().execute(m);



    }

    @Override
    public void onClick(View v) {
        if(mobile.getText().toString().isEmpty())
        {
            mobile.setError("Mobile No Empty");

        }
        else
        {
            m[0]="n";
            m[1]=id;
            m[2]=mobile.getText().toString();
new AsyncWork().execute(m);
        }
    }

    private Task<Map<String, String>> getUser(String userid) {
        // Take from Intent.

Map<String,String>uid=new HashMap<>();
uid.put("id",userid);



        return mFunctions
                .getHttpsCallable("getuser")
                .call(uid)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, String>>() {
                    @Override
                    public Map<String, String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        data= (Map<String, String>) task.getResult().getData();
                        return data;
                    }
                });
    }

    private Task<Map<String, String>> setUser(String userid[]) {
        // Take from Intent.

        Map<String,String>uid=new HashMap<>();
        uid.put("userid",userid[1]);
        uid.put("mobile",userid[2]);



        return mFunctions
                .getHttpsCallable("addmobile")
                .call(uid)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, String>>() {
                    @Override
                    public Map<String, String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        data= (Map<String, String>) task.getResult().getData();
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
    class AsyncWork extends AsyncTask<String,String,Void>
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
        protected Void doInBackground(String... voids) {
            if (voids[0].equals("user")) {
                getUser(voids[1])
                        .addOnCompleteListener(new OnCompleteListener<Map<String, String>>() {
                            @Override
                            public void onComplete(@NonNull Task<Map<String, String>> task) {
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
                                    mname.setText(data.get("name"));
                                    email.setText(data.get("email"));
                                    if (data.get("mobile") != null)
                                        mobile.setText(data.get("mobile"));
                                    Picasso.get().load(data.get("photourl")).placeholder(R.drawable.noimage).into(profileimg);


                                }
                                // ...
                            }
                        });
            }
            else
            {
                setUser(voids)
                        .addOnCompleteListener(new OnCompleteListener<Map<String, String>>() {
                            @Override
                            public void onComplete(@NonNull Task<Map<String, String>> task) {
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
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                                }
                                // ...
                            }
                        });
            }
                return null;
            }

    }
}
