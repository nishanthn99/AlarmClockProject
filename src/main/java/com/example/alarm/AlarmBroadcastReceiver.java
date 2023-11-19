package com.example.alarm;

import static android.content.Context.POWER_SERVICE;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    AlarmStructure alarmdata;
    int position;
    public void statuslist(AlarmStructure alarmdata,int position){
        this.alarmdata=alarmdata;
        this.position=position;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
//        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag");
//        wakeLock.acquire();
        Log.e("ns", "onReceive: donnnnnnn"+context);
//        mp=MediaPlayer.create(context,Settings.System.DEFAULT_ALARM_ALERT_URI);
//        mp.setLooping(false);
//        mp.start();
        AlarmManage alramnotice=new AlarmManage(context,intent);
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("actionname", intent.getAction()));

    }
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            if (action==AlarmManage.EXTRA_CANCEL)
                onTrackCancel();
            else
                onTrackSnooze();
        }
    };


    public void onTrackCancel() {

    }

    public void onTrackSnooze() {

    }

}



