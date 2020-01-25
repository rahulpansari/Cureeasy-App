


package com.example.cureeasy;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyFirebaseMessagingService extends FirebaseMessagingService  {
    String uid;
    private final String channel="CUREEASY Channel";
    FirebaseFunctions mFunctions;
    Map<String,String> result;
    int not_id=101;

    @NonNull

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prev", MODE_PRIVATE);
        uid=pref.getString("userid",null);
        mFunctions = FirebaseFunctions.getInstance();
        sendTokenToServer(s);

    }

    public void sendTokenToServer(String s1)
    {
        addToken(s1)
                .addOnCompleteListener(new OnCompleteListener<Map<String,String>>() {
                    @Override
                    public void onComplete(@NonNull Task<Map<String,String>> task) {
                        if (!task.isSuccessful()) {
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }

                            // ...
                        }
                        else
                        {
                            Log.e("Token Service","err");
                        }
                        // ...
                    }
                });
    }
    private Task<Map<String,String>> addToken(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("token", text);
        data.put("userid", uid);

        return mFunctions
                .getHttpsCallable("addtoken")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Map<String,String>>() {
                    @Override
                    public Map<String,String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        result = (Map<String,String>) task.getResult().getData();
                        return result;
                    }
                });
    }

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createnotificationchannel();
        Map<String, String> data = remoteMessage.getData();
        NotificationCompat.Builder n = new NotificationCompat.Builder(this, channel);
        // n.setSmallIcon(R.drawable.php);
        if (data.get("success").equals('1')) {
            n.setContentTitle(data.get("title"));


            n.setSmallIcon(R.drawable.al);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.al);
            n.setLargeIcon(bitmap);

            n.setContentText(data.get("body"));
            n.setPriority(NotificationCompat.PRIORITY_HIGH);
            Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_sound);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            // n.setContentIntent(landingPending);
            n.setAutoCancel(true);

            NotificationManagerCompat nm = NotificationManagerCompat.from(this);
            nm.notify(not_id, n.build());
        }
        else {
try
{
            String bl = "";
            String al = "";
            String bd = "";
            String ad = "";
            String medicine = data.get("medicine");
            n.setContentTitle(data.get("title"));


            n.setSmallIcon(R.drawable.al);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.al);
            n.setLargeIcon(bitmap);

            n.setContentText(data.get("body"));
            n.setPriority(NotificationCompat.PRIORITY_HIGH);
            Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_sound);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            // n.setContentIntent(landingPending);
            n.setAutoCancel(true);

            NotificationManagerCompat nm = NotificationManagerCompat.from(this);
            nm.notify(not_id, n.build());
            ArrayList<Map<String, String>> alist = new ArrayList<Map<String, String>>();

            String m0[] = medicine.split("\n");
            for (int i = 0; i < m0.length; i++) {
                Map<String, String> amap = new HashMap<>();
                String m1[] = m0[i].split(" ");
                amap.put("Medicine", m1[0]);
                amap.put("Quantity", m1[1]);
                amap.put("Time", m1[3]);
                alist.add(amap);

            }
            for (int i = 0; i < alist.size(); i++) {
                if (alist.get(i).get("Time").contains("BL")) {
                    bl = bl + alist.get(i).get("Medicine") + " " + alist.get(i).get("Quantity") + "\n";
                    Log.e("hhhuuu", bl);
                }
                if (alist.get(i).get("Time").contains("BD")) {
                    bd = bd + alist.get(i).get("Medicine") + " " + alist.get(i).get("Quantity") + "\n";
                    Log.e("hhhuuu", "bd");
                }
                if (alist.get(i).get("Time").contains("AL")) {
                    al = al + alist.get(i).get("Medicine") + " " + alist.get(i).get("Quantity") + "\n";
                    Log.e("hhhuuu", al);
                }
                if (alist.get(i).get("Time").contains("AD")) {
                    ad = ad + alist.get(i).get("Medicine") + " " + alist.get(i).get("Quantity") + "\n";
                    Log.e("hhhuuu", ad);
                }

            }
            DbHandler handler = new DbHandler(getApplicationContext());
            Calendar cal;
            cal = Calendar.getInstance();
            if (handler.getMedicineCount("BL") > 0) {
                bl = bl + handler.getMedicine("BL").getMedicine();
                handler.updateMedicine(new DbMedicine(bl, "BL"));
                /*final int SELF_REMINDER_HOUR = 8;
                int delay;
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                if(currentHourIn24Format< SELF_REMINDER_HOUR)
                {
                    delay=(SELF_REMINDER_HOUR-currentHourIn24Format)*60;
                }
                else
                {
                    delay=(24-currentHourIn24Format+SELF_REMINDER_HOUR)*60;
                }
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("BL");
                PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS).setInitialDelay(delay,TimeUnit.MINUTES)
                                .addTag("BL")
                                .build();



                WorkManager.getInstance(getApplicationContext())
                        .enqueue(saveRequest);*/
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 8);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 00);

                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 1, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
            } else if (!bl.isEmpty()) {
                handler.addMedicine(new DbMedicine(bl, "BL"));
                /*final int SELF_REMINDER_HOUR = 8;
                int delay;
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                if(currentHourIn24Format< SELF_REMINDER_HOUR)
                {
                    delay=(SELF_REMINDER_HOUR-currentHourIn24Format)*60;
                }
                else
                {
                    delay=(24-currentHourIn24Format+SELF_REMINDER_HOUR)*60;
                }
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("BL");
                PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS).setInitialDelay(delay,TimeUnit.MINUTES)
                                .addTag("BL")
                                .build();



                WorkManager.getInstance(getApplicationContext())
                        .enqueue(saveRequest);*/
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 8);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 00);
                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 1, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);

            }

            if (handler.getMedicineCount("AL") > 0) {
                al = al + handler.getMedicine("AL").getMedicine();
                handler.updateMedicine(new DbMedicine(al, "AL"));
               /* final int SELF_REMINDER_HOUR = 16;
                int delay;
               /* Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                if(currentHourIn24Format< SELF_REMINDER_HOUR)
                {
                    delay=(SELF_REMINDER_HOUR-currentHourIn24Format)*60;
                }
                else
                {
                    delay=(24-currentHourIn24Format+SELF_REMINDER_HOUR)*60;
                }*/
               /* WorkManager wmanager;
                //WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("AL");
               PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS).setInitialDelay(60,TimeUnit.SECONDS)
                                .addTag("AL")
                                .build();



                wmanager=WorkManager.getInstance(getApplicationContext());
                        wmanager.enqueue(saveRequest);*/

                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 15);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 00);
                Calendar rightnow = Calendar.getInstance();
                int hr = rightnow.get(Calendar.HOUR_OF_DAY);
                Log.e("hhf", hr + "");
                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 2, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
            } else if (!al.isEmpty()) {
                handler.addMedicine(new DbMedicine(al, "AL"));
               /* final int SELF_REMINDER_HOUR = 15;
                int delay;
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                if(currentHourIn24Format< SELF_REMINDER_HOUR)
                {
                    delay=(SELF_REMINDER_HOUR-currentHourIn24Format)*60;
                }
                else
                {
                    delay=(24-currentHourIn24Format+SELF_REMINDER_HOUR)*60;
                }
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("AL");
                PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS).setInitialDelay(1,TimeUnit.MINUTES)
                                .addTag("AL")
                                .build();



                WorkManager.getInstance(getApplicationContext())
                        .enqueueUniquePeriodicWork("AL", ExistingPeriodicWorkPolicy.KEEP,saveRequest);*/
                Calendar rightnow = Calendar.getInstance();
                int hr = rightnow.get(Calendar.HOUR_OF_DAY);
                Log.e("hhfempty", hr + "");
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 15);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 00);
                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 2, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
            }
            if (handler.getMedicineCount("BD") > 0) {
                bd = bd + handler.getMedicine("BD").getMedicine();
                handler.updateMedicine(new DbMedicine(bd, "BD"));
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 19);
                cal.set(Calendar.MINUTE, 20);
                cal.set(Calendar.SECOND, 00);
                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 3, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
            } else if (!bd.isEmpty()) {
                handler.addMedicine(new DbMedicine(bd, "BD"));
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 20);
                cal.set(Calendar.MINUTE, 58);
                cal.set(Calendar.SECOND, 00);
                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 3, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);

              /*  final int SELF_REMINDER_HOUR = 20;
                int delay;
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                if(currentHourIn24Format< SELF_REMINDER_HOUR)
                {
                    delay=(SELF_REMINDER_HOUR-currentHourIn24Format)*60;
                }
                else
                {
                    delay=(24-currentHourIn24Format+SELF_REMINDER_HOUR)*60;
                }
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("BD");
                PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS).setInitialDelay(delay,TimeUnit.MINUTES)
                                .addTag("BD")
                                .build();



                WorkManager.getInstance(getApplicationContext())
                        .enqueue(saveRequest);
*/

            }
            if (handler.getMedicineCount("AD") > 0) {
                ad = ad + handler.getMedicine("AD").getMedicine();
                handler.updateMedicine(new DbMedicine(ad, "AD"));
               /* final int SELF_REMINDER_HOUR = 21;
                int delay;
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                if(currentHourIn24Format< SELF_REMINDER_HOUR)
                {
                    delay=(SELF_REMINDER_HOUR-currentHourIn24Format)*60;
                }
                else
                {
                    delay=(24-currentHourIn24Format+SELF_REMINDER_HOUR)*60;
                }
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("AD");
                PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS).setInitialDelay(delay,TimeUnit.MINUTES)
.addTag("AD")
                                .build();



                WorkManager.getInstance(getApplicationContext())
                        .enqueue(saveRequest);

*/


                Log.e("hira", ad);

                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 21);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 00);
                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 4, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
            } else if (!ad.isEmpty()) {
                handler.addMedicine(new DbMedicine(ad, "AD"));
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 21);
                // Log.e("hira",ad);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
                alarmintent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getApplicationContext(), 4, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);
                else
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (long) (1000 * 60 * 60 * 24), pendingIntent);

               /* final int SELF_REMINDER_HOUR = 21;
                int delay;
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                if(currentHourIn24Format< SELF_REMINDER_HOUR)
                {
                    delay=(SELF_REMINDER_HOUR-currentHourIn24Format)*60;
                }
                else
                {
                    delay=(24-currentHourIn24Format+SELF_REMINDER_HOUR)*60;
                }
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("AD");
                PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS).setInitialDelay(delay,TimeUnit.MINUTES)
                                .addTag("AD")
                                .build();



                WorkManager.getInstance(getApplicationContext())
                        .enqueue(saveRequest);*/


            }
        }
catch (Exception e)
{

}
        }
    }
    public void createnotificationchannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name="CureEasy Noitifications";

            int imp=NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel nc= new NotificationChannel(channel,name,imp);
            //nc.setDescription(description);


            nc.enableVibration(true);

            NotificationManager notMan=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notMan.createNotificationChannel(nc);

        }
    }

}
