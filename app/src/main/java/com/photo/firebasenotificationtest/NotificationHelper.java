package com.photo.firebasenotificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String TOPIC_UPDATE_NEWS = "UpdateNews";
    public static final String CHANNEL_NAME = "Main Notifications";
    public static final String CHANNEL_DESCRIPTION = "Notification description";
    public static final String CHANNEL_ID = "MyNotificationChannel";
    public static final int NOTIFICATION_ID = 1000;
    private static NotificationManager notificationManager;
    private Context base;

    public NotificationHelper(Context base) {
        super(base);
        this.base = base;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationChannel.enableVibration(true);
        notificationChannel.enableLights(true);
        notificationChannel.canShowBadge();
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationChannel.setLightColor(getResources().getColor(R.color.colorAccent));
        getNotificationManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) base.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    // now lets create notification
    public void notify(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(base, CHANNEL_ID);
        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentText(message);
        builder.setSound(notificationUri);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        getNotificationManager().notify(NOTIFICATION_ID, builder.build());
    }

}
