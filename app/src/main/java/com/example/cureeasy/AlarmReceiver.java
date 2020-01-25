package com.example.cureeasy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("bpolo","hello");
        Intent intent1 = new Intent(context, AlarmService.class);
        context.startService(intent1);
    }
}
