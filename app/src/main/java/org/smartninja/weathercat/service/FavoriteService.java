package org.smartninja.weathercat.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.smartninja.weathercat.CityDetailFragment;
import org.smartninja.weathercat.R;
import org.smartninja.weathercat.SecondActivity;
import org.smartninja.weathercat.model.WeatherData;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by blaz on 05/06/16.
 */
public class FavoriteService extends IntentService {

    private final OkHttpClient client = new OkHttpClient();

    public FavoriteService() {
        super("FavoriteCity");
    }

    @Override protected void onHandleIntent(Intent intent) {

        SharedPreferences preferences = getSharedPreferences("weathercat", Context.MODE_PRIVATE);
        long cityId = preferences.getLong("cities", -1);

        if (cityId != -1) {

            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host("api.openweathermap.org")
                    .addPathSegment("/data/2.5/weather")
                    .addQueryParameter("id", String.valueOf(cityId))
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("appid", "264eb32663a1e4e9cb406b10f7186248")
                    .build();


            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Gson gson = new Gson();
                WeatherData data = gson.fromJson(response.body().string(), WeatherData.class);

                showNotification(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void showNotification(WeatherData data) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String title = String.format("Weather for %s", data.getName());
        Notification.Builder builder = new Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                .setAutoCancel(true);

        BigTextStyle style = new BigTextStyle(builder)
                .bigText(String.format("There is %.2f°C in %s", data.getMain().getTemp(), data.getName()))
                .setSummaryText(String.format("%.2f°C", data.getMain().getTemp()))
                .setBigContentTitle(title);
        builder.setStyle(style);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(CityDetailFragment.EXTRA_WEATHER_DATA, data);
        builder.setContentIntent(PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        notificationManager.notify(1, builder.build());

        expandNotifications();

    }

    private void expandNotifications() {

        try {
            Object service = getSystemService("statusbar");
            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusbarManager.getMethod( "expand" );
            expand.invoke( service );
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
