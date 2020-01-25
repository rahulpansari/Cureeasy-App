package com.example.cureeasy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
RecyclerView chatrecycler;
TextView toolbartitle;
ChatAdapter adapter;
EditText chat_msg;
FloatingActionButton fab;
CircleImageView profile;
Toolbar toolbar;
ArrayList<Message> messages;
String msg,fname,uuid,tname,furl,turl,ttkn,tti,fid,ftoken;
    String photo;

    private DatabaseReference mDatabase;
    FirebaseFunctions mFunctions;
    Map<String,String>result2;
    ArrayList<Map<String,String>> mmessages;
    Map<String, ArrayList<Map<String,String>> >result3;
Map<String,String> getIntents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prev", MODE_PRIVATE);
        uuid=pref.getString("userid",null);
        toolbar=findViewById(R.id.toolbar);
        toolbartitle=findViewById(R.id.toolbar_title);
        profile=findViewById(R.id.profile);
        getIntents=new HashMap<>();
        chatrecycler=findViewById(R.id.chat_recycler_view);
        fab=findViewById(R.id.fab);
        chat_msg=findViewById(R.id.chat_edit_msg);
messages=new ArrayList<>();
        mmessages=new ArrayList<>();
        getArrayIntent();
        setToolbar();
        setChat();

       setSupportActionBar(toolbar);



    }
    public void setAdapter(ArrayList<Map<String,String>> msg,String p)
    {
        adapter=new ChatAdapter(this,msg,uuid,p);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.VERTICAL);
        chatrecycler.setLayoutManager(ll);
        chatrecycler.setAdapter(adapter);
    }
    public void getArrayIntent()
    {
        Intent i=getIntent();
        getIntents.put("name",i.getStringExtra("name"));
        getIntents.put("userid",i.getStringExtra("userid"));
        getIntents.put("photourl",i.getStringExtra("photourl"));
        getIntents.put("token",i.getStringExtra("token"));
        Log.e("hi",getIntents.get("token"));

        getChat(uuid,getIntents.get("userid"),getIntents.get("photourl"));
        setAdapter(mmessages,getIntents.get("photourl"));
    }

    public void setToolbar()
    {
        Picasso.get().load(getIntents.get("photourl")).placeholder(R.drawable.noimage).into(profile);
        toolbartitle.setText(getIntents.get("name"));
    }

    public void setChat()
    {
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        msg=chat_msg.getText().toString();
        if(msg!=null)
        {chat_msg.setText("");
            Map<String,String>n=new HashMap<String, String>();

            n.put("from",uuid);
            n.put("to",getIntents.get("userid"));
            n.put("message",msg);

            mmessages.add(n);
            chatrecycler.scrollToPosition(messages.size()-1);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("prev", MODE_PRIVATE);
            fname=pref.getString("username",null);
            fid=pref.getString("userid",null);
            ftoken=pref.getString("token",null);
            //Log.e("hold",ftoken.toString());
            tname=getIntents.get("name");
            turl=getIntents.get("photourl");
            ttkn=getIntents.get("token");
            tti=getIntents.get("userid");
            furl=pref.getString("photourl",null);
            adapter.notifyDataSetChanged();
            firebasechat(uuid,msg,getIntents.get("userid"),fname,getIntents.get("token"),furl,tname,turl,ttkn,tti,fid,ftoken);
        }
    }

    public void firebasechat(final String from,final String m,final String to,String name,String token,String fuurl,String tn,String tu,String tttkn,String ttti,String ffi,String fft)
    {
        result2=new HashMap<String,String>();
        mFunctions = FirebaseFunctions.getInstance();
        startchat(from,m,to,name,token,fuurl,tn,tu,tttkn,ttti,ffi,fft)
                .addOnCompleteListener(new OnCompleteListener<Map<String,String>>() {
                    @Override
                    public void onComplete(@NonNull Task<Map<String,String>> task) {
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
                        else
                        {



                        }
                        // ...
                    }
                });

    }
    private Task<Map<String,String>> startchat(String fro,String m,String t,String name,String token,String fuurl,String tn,String tu,String tttkn,String ttti,String ffi,String fft) {
        // Take from Intent.
        Map<String, Object> data = new HashMap<>();

        data.put("from",fro);
        data.put("to",t);
        data.put("message",m);
        data.put("name",name);
        data.put("token",token);
        data.put("fuserid",name);
        data.put("fprofile",fuurl);
        data.put("tuserid",tn);
        data.put("tprofile",tu);
        data.put("ttoken",tttkn);
        data.put("tid",ttti);
        data.put("fid",ffi);
        data.put("fft",fft);
        return mFunctions
                .getHttpsCallable("chatmessage")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, String>>() {
                    @Override
                    public  Map<String, String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        result2 = ( Map<String, String>) task.getResult().getData();
                        return result2;
                    }
                });
    }

    public void getChat(final String from,final String to,String url)
    {photo=url;
        result3=new HashMap<String, ArrayList<Map<String,String>>>();
        mFunctions = FirebaseFunctions.getInstance();
        getstartchat(from,to)
                .addOnCompleteListener(new OnCompleteListener<Map<String, ArrayList<Map<String,String>>>>() {
                    @Override
                    public void onComplete(@NonNull Task<Map<String, ArrayList<Map<String,String>>>> task) {
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
                        else
                        {mmessages=task.getResult().get("data");
                            setAdapter(mmessages,photo);


                        }
                        // ...
                    }
                });

    }

    private Task<Map<String, ArrayList<Map<String,String>>>> getstartchat(String fro,String t) {
        // Take from Intent.
        Map<String, Object> data = new HashMap<>();

        data.put("from",fro);
        data.put("to",t);


        return mFunctions
                .getHttpsCallable("getchatmessage")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Map<String, ArrayList<Map<String,String>>>>() {
                    @Override
                    public Map<String, ArrayList<Map<String,String>>> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        result3 = (Map<String, ArrayList<Map<String,String>>>) task.getResult().getData();
                        return result3;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DESTROY","ACTIVITY IS DESTROYED");
    }

public void onResume()
{
    super.onResume();

}

public void onStop()
{
    super.onStop();
}
}
