package com.uta.picartstudiosandep.waterfallcallerledscreen.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

@SuppressLint({"NewApi"})
public class NotificationService extends NotificationListenerService {
    static StatusBarNotification status_note;
    Context scontext;

    public StatusBarNotification[] getActiveNotifications() {
        return super.getActiveNotifications();
    }

    public void onCreate() {
        super.onCreate();
        this.scontext = getApplicationContext();
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);
        try {
            status_note = statusBarNotification;
            String packageName = statusBarNotification.getPackageName();
            Intent intent = new Intent("Msg");
            intent.putExtra("package", packageName);
            LocalBroadcastManager.getInstance(this.scontext).sendBroadcast(intent);
        } catch (Exception statusBarNotification2) {
            statusBarNotification2.printStackTrace();
        }
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
    }
}
