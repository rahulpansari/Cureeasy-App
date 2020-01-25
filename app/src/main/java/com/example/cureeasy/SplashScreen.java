package com.example.cureeasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String uuid;
                SharedPreferences pref = getApplicationContext().getSharedPreferences("prev", MODE_PRIVATE);
                uuid=pref.getString("user",null);
                if(uuid==null)
                { finish();
                    startActivity(new Intent(SplashScreen.this, AuthActivity.class));

                }
                else if(uuid.equals("patient"))
                {
                    finish();
                    startActivity(new Intent(SplashScreen.this, PatientHome.class));
                }
                else if(uuid.equals("doctor"))
                {
                    finish();
                    startActivity(new Intent(SplashScreen.this, DoctorHome.class));
                }
             }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
