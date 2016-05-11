package org.smartninja.weathercat;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.smartninja.weathercat.model.WeatherData;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FavoritePlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FavoritePlacesAdapter(this, FavoritePlacesAdapter.DATA);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WeatherData item = adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(SecondActivity.EXTRA_WEATHER_DATA, item);
                startActivity(intent);
            }
        });


    }
}
