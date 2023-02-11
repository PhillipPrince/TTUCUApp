package com.example.ttucuapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class alertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationClass notificationHelper=new NotificationClass(context);
        NotificationCompat.Builder nb=notificationHelper.createNotification("","");
        notificationHelper.getmManager().notify(1, nb.build());
    }
}
