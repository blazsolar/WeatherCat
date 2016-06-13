package org.smartninja.weathercat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.smartninja.weathercat.model.WeatherData;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity implements ShowDetail {

    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = findViewById(R.id.content) != null;

        if (savedInstanceState != null) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.content);
            if (!isLandscape && fragment != null) {
                getFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        }

    }

    @Override
    public void showDetail(WeatherData item) {

        if (isLandscape) {

            Bundle arguments = new Bundle();
            arguments.putParcelable(CityDetailFragment.EXTRA_WEATHER_DATA, item);

            Fragment fragment = new CityDetailFragment();
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(CityDetailFragment.EXTRA_WEATHER_DATA, item);
            startActivity(intent);
        }
    }
}
