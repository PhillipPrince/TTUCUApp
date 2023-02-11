package com.example.ttucuapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationClass extends ContextWrapper {
    private  NotificationManager mManager;
    NotificationManagerCompat notificationManagerCompat;
    public static final String chanel="Channel";
    public NotificationClass(Context base) {
        super(base);
    }
    public NotificationCompat.Builder createNotification(String title, String text) {
        notificationManagerCompat=NotificationManagerCompat.from(this);
        Intent intent=new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1, intent, 0);
        Notification notification=new NotificationCompat.Builder(this, chanel)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.culogo)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .build();
        notificationManagerCompat.notify(1, notification);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(
                    chanel,
                    "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationChannel.enableLights(true);
            notificationChannel.setDescription("Notified");
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        return null;
    }
    public  NotificationManager getmManager(){
        if(mManager==null){
            mManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

}
