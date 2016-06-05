package org.smartninja.weathercat.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import org.smartninja.weathercat.service.FavoriteService;

/**
 * Created by blaz on 05/06/16.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        Log.d("BootReceiver", "Booted");
        SharedPreferences preferences = context.getSharedPreferences("weathercat", Context.MODE_PRIVATE);
        long cityId = preferences.getLong("cities", -1);

        if (cityId != -1) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent favoriteIntent = new Intent(context, FavoriteService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 2, favoriteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 10 * 1000, 60 * 1000, pendingIntent);
        }

    }

}
