package de.h_da.fbi.nzse.thediaryapp.models;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import de.h_da.fbi.nzse.thediaryapp.activities.HomeScreenActivity;
import de.h_da.fbi.nzse.thediaryapp.R;

/**
 *  Class which Handles the App's notification. When the broadcast is received, a notification is being created.
 */
public class ReminderBroadcast extends BroadcastReceiver {
    private static final String channelId = "notifyUser";
    private static final int notificationId = 200;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentHome = new Intent(context, HomeScreenActivity.class);

        intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentHome, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.drawable.ic_sentiment_happy_outlined_24px)
                .setContentTitle("Pocket Diary")
                .setContentText("Vergiss nich an dich zu denken und einen kleine Eintrag in Pocket Diary zu hinterlassen.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        Log.d("Broadcast", "Broadcast received");

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(notificationId,builder.build());

    }
}
