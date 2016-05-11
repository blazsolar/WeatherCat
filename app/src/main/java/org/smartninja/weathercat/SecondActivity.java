package org.smartninja.weathercat;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.smartninja.weathercat.model.WeatherData;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_WEATHER_DATA = "weather_data";

    private WeatherData weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        weatherData = extras.getParcelable(EXTRA_WEATHER_DATA);

        setTitle(weatherData.getName());

        TextView tempView = (TextView) findViewById(R.id.temperature);
        tempView.setText(getString(R.string.temperature, weatherData.getMain().getTemp()));
    }
}
