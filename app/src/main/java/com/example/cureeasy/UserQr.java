package com.example.cureeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

public class UserQr extends AppCompatActivity {
WebView userqr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr);
        userqr=findViewById(R.id.qr_webview);
        userqr.getSettings().setJavaScriptEnabled(true);
        loadQR();

    }
    public void loadQR()
    {  String uuid;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prev", MODE_PRIVATE);
        uuid=pref.getString("userid",null);
        userqr.loadUrl("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+uuid);

    }

}
