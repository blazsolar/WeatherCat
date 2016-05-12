package org.smartninja.weathercat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.smartninja.weathercat.model.WeatherData;

/**
 * Created by blaz on 08/05/16.
 */
public class FavoritePlacesAdapter extends BaseAdapter {

    private static final String TAG = "FavoritePlacesAdapter";

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
    private final Resources resources;
    private WeatherData[] data;

    public FavoritePlacesAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.resources = context.getResources();
    }

    public void setData(WeatherData[] data) {
        this.data = data;
        notifyDataSetChanged();
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
            view = layoutInflater.inflate(R.layout.list_item_city, parent, false);
        }

        WeatherData item = getItem(position);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(item.getName());

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        new ImageTask(resources, imageView).execute(R.drawable.ic_sun);

        return view;
    }

    private static class ImageTask extends AsyncTask<Integer, Void, Drawable> {

        private final Resources resources;
        private final ImageView imageView;

        public ImageTask(Resources resources, ImageView imageView) {
            this.resources = resources;
            this.imageView = imageView;
        }

        @Override
        protected Drawable doInBackground(Integer... params) {
            return resources.getDrawable(params[0], null);
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            imageView.setImageDrawable(drawable);
        }
    }

}
