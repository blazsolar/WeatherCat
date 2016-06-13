package org.smartninja.weathercat;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.smartninja.weathercat.model.WeatherData;

import java.io.IOException;

public class CurrentLocationActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    private final OkHttpClient client = new OkHttpClient();
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        setActionBar((Toolbar) findViewById(R.id.toolbar));

        googleApiClient = new GoogleApiClient.Builder(this, this, this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    googleApiClient.connect();
                } else {
                    finish();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override public void onConnected(@Nullable Bundle bundle) {
        getLastLocation();
    }

    @Override public void onConnectionSuspended(int i) {
        // nothing to do
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, 1);
            } catch (SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Could not retreive location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public void onLocationChanged(Location location) {
        makeRequest(location);
    }

    @SuppressWarnings("MissingPermission") private void getLastLocation() {
        if (!googleApiClient.isConnected()) {
            if (!googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        } else if (checkForPermission()) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if (lastLocation != null && lastLocation.getTime() > System.currentTimeMillis() - 15 * 60 * 1000) {
                makeRequest(lastLocation);
            } else {
                LocationRequest locationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10 * 1000)
                        .setFastestInterval(1 * 1000)
                        .setNumUpdates(1);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            }
        }
    }

    private boolean checkForPermission() {

        if (checkSelfPermission(permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ permission.ACCESS_FINE_LOCATION }, 1);
            return false;
        } else {
            return true;
        }

    }

    private void makeRequest(Location location) {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegment("/data/2.5/weather")
                .addQueryParameter("lat", String.valueOf(location.getLatitude()))
                .addQueryParameter("lon", String.valueOf(location.getLongitude()))
                .addQueryParameter("units", "metric")
                .addQueryParameter("appid", "264eb32663a1e4e9cb406b10f7186248")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Request request, IOException e) {
                // show error
            }

            @Override public void onResponse(Response response) throws IOException {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Gson gson = new Gson();
                final WeatherData data = gson.fromJson(response.body().string(), WeatherData.class);

                if (data.getId() != 0) {
                    CurrentLocationActivity.this.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            showData(data);
                        }
                    });
                }

            }
        });

    }

    private void showData(WeatherData data) {

        ((TextView) findViewById(R.id.temperature))
                .setText(getString(R.string.temperature, data.getMain().getTemp()));

        setTitle(data.getName());

    }

}
