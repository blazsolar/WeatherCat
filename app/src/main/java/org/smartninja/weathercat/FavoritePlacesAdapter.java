package org.smartninja.weathercat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.smartninja.weathercat.model.WeatherData;

/**
 * Created by blaz on 08/05/16.
 */
public class FavoritePlacesAdapter extends BaseAdapter {

    public static final WeatherData[] DATA = new WeatherData[] {
            new WeatherData(3239318, "Mestna Občina Ljubljana", 13.91f),
            new WeatherData(3186843, "Občina Žalec", 13.91f),
            new WeatherData(3192062, "Občina Radovljica", 13.91f),
            new WeatherData(3197378, "Kranj", 13.91f),
            new WeatherData(3194351, "Novo Mesto", 13.91f),
            new WeatherData(3198647, "Jesenice", 13.91f),
            new WeatherData(3192241, "Ptuj", 13.91f),
            new WeatherData(3195506, "Maribor", 13.91f),
            new WeatherData(5128638, "New York", 13.91f),
            new WeatherData(1689973, "San Francisco", 13.91f),
            new WeatherData(3186886, "Zagreb", 13.91f),
            new WeatherData(2759794, "Amsterdam", 13.91f),
            new WeatherData(5056033, "London", 13.91f),
            new WeatherData(2950159, "Berlin", 13.91f),
            new WeatherData(2988507, "Paris", 13.91f),
            new WeatherData(292223, "Dubai", 13.91f),
            new WeatherData(1609350, "Bangkok", 13.91f),
            new WeatherData(1138958, "Kabul", 13.91f)
    };

    private final LayoutInflater layoutInflater;
    private WeatherData[] data;

    public FavoritePlacesAdapter(Context context, WeatherData[] data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data != null) {git 
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
