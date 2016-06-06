package org.smartninja.weathercat;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import org.smartninja.weathercat.model.WeatherData;
import org.smartninja.weathercat.service.FavoriteService;

/**
 * Created by blaz on 26/05/16.
 */

public class CityDetailFragment extends Fragment {

    public static final String EXTRA_WEATHER_DATA = "weather_data";

    private WeatherData weatherData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle extras = getArguments();
        weatherData = extras.getParcelable(EXTRA_WEATHER_DATA);

        getActivity().setTitle(weatherData.getName());

        TextView tempView = (TextView) view.findViewById(R.id.temperature);
        tempView.setText(getString(R.string.temperature, weatherData.getMain().getTemp()));

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_notify);

        SharedPreferences preferences = getContext().getSharedPreferences("weathercat", Context.MODE_PRIVATE);
        long selectedId = preferences.getLong("cities", -1);
        checkBox.setChecked(selectedId == weatherData.getId());

        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getContext().getSharedPreferences("weathercat", Context.MODE_PRIVATE);

                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getContext(), FavoriteService.class);
                PendingIntent pendingIntent = PendingIntent.getService(getContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (isChecked) {
                    preferences.edit()
                            .putLong("cities", weatherData.getId())
                            .apply();

                        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 10 * 1000, 60 * 1000, pendingIntent);
                } else {
                    preferences.edit()
                            .remove("cities")
                            .apply();


                    alarmManager.cancel(pendingIntent);
                }
            }
        });
    }


}
