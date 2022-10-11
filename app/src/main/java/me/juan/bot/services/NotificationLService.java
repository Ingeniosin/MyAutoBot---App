package me.juan.bot.services;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationLService extends NotificationListenerService {


    @Override
    public void onCreate() {
        Log.i("NotificationListener", "onCreate");
       }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification mNotification=sbn.getNotification();
        if (mNotification!=null){
            Bundle extras = mNotification.extras;
            String log = "onNotificationPosted " + sbn.getPackageName() + " " + sbn.getNotification().tickerText + " " + sbn.getNotification().extras.get("android.text");
            Log.i("NotificationListener", log);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }

}
