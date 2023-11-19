package com.example.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.IBinder;

import android.provider.MediaStore;
import android.provider.Settings;



import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import android.support.v4.media.session.MediaSessionCompat;

public class AlarmManage extends Service{
    private long offsetTime;
    private static final String CHANNEL_ID="ALARMM";
    private static final int ALARM_NOTI_ID=99;
    private static final int ALARMINTENT_ID=9;
    public static final String EXTRA_SNOOZE="snooze";
    public static final String EXTRA_CANCEL="cancel";
//    public AlarmManage(Context context, int hour, int minute) {
//        offsetTime=((hour*3600)+(minute*60))*1000+System.currentTimeMillis();
//        //offsetTime=20000+System.currentTimeMillis();
//        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent=new Intent(getApplicationContext(),AlarmBroadcastReceiver.class);
//        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.set(AlarmManager.RTC,offsetTime,pendingIntent);
//    }
    public AlarmManage(Context context,Intent intent){
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat( context, "tag");
        MediaPlayer mp= MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.setLooping(true);
        mp.start();

        Intent snoozeIntent = new Intent(context, AlarmBroadcastReceiver.class);
        snoozeIntent.setAction(EXTRA_SNOOZE);
        snoozeIntent.putExtra(EXTRA_SNOOZE, 0);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_MUTABLE);

        Intent cancelIntent = new Intent(context, AlarmBroadcastReceiver.class);
        snoozeIntent.setAction(EXTRA_SNOOZE);
        snoozeIntent.putExtra(EXTRA_SNOOZE, 0);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context, 0, cancelIntent, PendingIntent.FLAG_MUTABLE);

        Drawable drawable= ResourcesCompat.getDrawable(context.getResources(),R.drawable.img,null);
        BitmapDrawable bitmapDrawable=(BitmapDrawable)drawable;
        Bitmap bitmap=bitmapDrawable.getBitmap();

        NotificationCompat.BigPictureStyle bigPictureStyle=new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setSummaryText("");
        bigPictureStyle.bigPicture(bitmap);

//        Intent fullScreenIntent = new Intent(context, MainActivity.class);
//        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentCancel = new Intent(context, AlarmBroadcastReceiver.class)
                .setAction(EXTRA_CANCEL);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentSnooze = new Intent(context, AlarmBroadcastReceiver.class)
                .setAction(EXTRA_CANCEL);
        PendingIntent pendingIntentSnooze = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent intentnoti=new Intent(context,MainActivity.class);
        PendingIntent pendingIntentnoti=PendingIntent.getActivity(context,ALARMINTENT_ID,intentnoti,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification=new NotificationCompat.Builder(context)
                    .setContentTitle("Alarm Notification")
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.img)
                    .setContentText("ALARM")
                    .setSubText("DO YOUR WORK")
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntentnoti)
                    //.setStyle(bigPictureStyle)
                    //.addAction(new NotificationCompat.Action(R.drawable.img,"snooze",snoozePendingIntent))
                    //.addAction(new NotificationCompat.Action(R.drawable.img,"alarm",cancelPendingIntent))
                    .addAction(R.drawable.pause, "Previous", pendingIntentCancel)
                    .addAction(R.drawable.stopwatch, "Play", pendingIntentSnooze)
                    .setSound(Settings.System.DEFAULT_RINGTONE_URI)
                    //.setFullScreenIntent(fullScreenPendingIntent,true)
//                    .setStyle(new Notification.MediaStyle()
//                            .setMediaSession(mySession))
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1).setMediaSession(mediaSessionCompat.getSessionToken()))
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"Alarm channel",NotificationManager.IMPORTANCE_HIGH));
        }
        else {
            notification = new Notification.Builder(context)
                    .setContentTitle("Alarm Notification")
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.img)
                    .setContentText("ALARM")
                    .setSubText("DO YOUR WORK")
                    .setContentIntent(pendingIntentnoti)
                    .build();
        }
        notificationManager.notify(ALARM_NOTI_ID,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }
}
