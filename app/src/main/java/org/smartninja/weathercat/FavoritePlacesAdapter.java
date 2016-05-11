package org.smartninja.weathercat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.smartninja.weathercat.model.WeatherData;

import java.util.List;

/**
 * Created by blaz on 08/05/16.
 */
public class FavoritePlacesAdapter extends BaseAdapter {

    public static final WeatherData[] DATA = new WeatherData[] {
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f)
    };

    private final LayoutInflater layoutInflater;
    private WeatherData[] data;

    public FavoritePlacesAdapter(Context context, WeatherData[] data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.length;
        } else {
            return 0;
        }
    }

    @Override
    public WeatherData getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        WeatherData item = getItem(position);

        TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(item.getName());

        return view;
    }

}
