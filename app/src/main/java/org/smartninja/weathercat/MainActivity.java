package org.smartninja.weathercat;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.smartninja.weathercat.model.Cities;
import org.smartninja.weathercat.model.WeatherData;

import java.io.IOException;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FavoritePlacesAdapter adapter;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FavoritePlacesAdapter(this);
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

        requestCities();

    }

    private void requestCities() {

        String cityIds = TextUtils.join(",", new Integer[] {
                3239318, 3186843, 3192062, 3197378, 3194351, 3198647, 3192241, 3195506, 5128638,
                1689973, 3186886, 2759794, 5056033, 2950159, 2988507, 292223, 1609350, 1138958
        });

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegment("/data/2.5/group")
                .addQueryParameter("id", cityIds)
                .addQueryParameter("units", "metric")
                .addQueryParameter("appid", "264eb32663a1e4e9cb406b10f7186248")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Gson gson = new Gson();
                Cities cities = gson.fromJson(response.body().string(), Cities.class);
                adapter.setData(cities.getList());
            }
        });

    }
}
