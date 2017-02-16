package com.usuario.phonebroadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;

/**
 * Created by usuario on 15/02/17.
 */

public class Phone_BroadcastReceiver extends BroadcastReceiver {
    private final int CALLNOTIFICATION = 1;


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String state = bundle.getString(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String number = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

                Intent newIntent = new Intent(context, PhoneActivity.class);
                newIntent.putExtra("number", number);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, CALLNOTIFICATION, newIntent, PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle(context.getString(R.string.app_name));
                builder.setContentText("Llamada del numero: " + number);
                builder.setSmallIcon(android.R.drawable.sym_call_incoming);
                builder.setAutoCancel(true);

                //Parámetros extras.
                builder.setDefaults(Notification.DEFAULT_VIBRATE);
                builder.setDefaults(Notification.DEFAULT_LIGHTS);

                //Añadir el objeto PendingIntent a la notificación.
                builder.setContentIntent(pendingIntent);

                //Añadir la notificación al NotificationManager.
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                notificationManager.notify(CALLNOTIFICATION, builder.build());
            }
        }
    }
}
