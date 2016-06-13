package org.smartninja.weathercat;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = new Bundle();
        arguments.putParcelable(CityDetailFragment.EXTRA_WEATHER_DATA,
                getIntent().getExtras().getParcelable(CityDetailFragment.EXTRA_WEATHER_DATA));

        Fragment fragment = new CityDetailFragment();
        fragment.setArguments(arguments);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }
}
