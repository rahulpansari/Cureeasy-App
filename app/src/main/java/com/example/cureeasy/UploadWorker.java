package com.example.cureeasy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class UploadWorker extends Worker {
    public int not_id=101;
    private final String channel="Cureeasy Channel";
    Context c;
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        c=context;
    }

    @NonNull
    @Override
    public Result doWork() {
        // do what you want to do
        Log.e("helloh","hh");
        DbHandler handler=new DbHandler(getApplicationContext());
        DbMedicine dmed;
        // Database d=handler.getData(System.currentTimeMillis());
        //Log.e("database",d.mname);
        Log.e("here","alarm");
        createnotificationchannel();
        Calendar rightnow=Calendar.getInstance();
        int hr=rightnow.get(Calendar.HOUR_OF_DAY);
        dmed=new  DbMedicine();
        if(hr==8)
        {
            dmed=handler.getMedicine("BL");
            shownotification( dmed);
        }
        else if(hr==16)
        {Log.e("kyubhai","kyubhai");
            dmed=handler.getMedicine("AL");
            shownotification( dmed);
        }
        else if(hr==20)
        {
            dmed=handler.getMedicine("BD");
            shownotification( dmed);
        }
        else if(hr==21)
        {Log.e("here","gygygygy");
            dmed=handler.getMedicine("AD");
            shownotification( dmed);
        }
        return Result.success();
    }
    public void createnotificationchannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name="CureEasy Noitifications";

            int imp= NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel nc= new NotificationChannel(channel,name,imp);
            //nc.setDescription(description);


            nc.enableVibration(true);

            NotificationManager notMan=(NotificationManager)c.getSystemService(NOTIFICATION_SERVICE);
            notMan.createNotificationChannel(nc);

        }
    }
    public void shownotification(DbMedicine dmed)
    {
        NotificationCompat.Builder n= new NotificationCompat.Builder(c,channel);
        n.setContentTitle("Medicine Reminder");


        n.setSmallIcon(R.drawable.al);
        Bitmap bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.al);
        n.setLargeIcon(bitmap);
        n.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(dmed.getMedicine()));
        Log.e("hir",dmed.getMedicine());
        n.setContentText("Take Medicines");
        n.setPriority(NotificationCompat.PRIORITY_HIGH);
        Uri notification = Uri.parse("android.resource://" + c.getPackageName() + "/" + R.raw.notification_sound);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        // n.setContentIntent(landingPending);
        n.setAutoCancel(true);

        NotificationManagerCompat nm = NotificationManagerCompat.from(c);
        nm.notify(not_id, n.build());
    }
}
