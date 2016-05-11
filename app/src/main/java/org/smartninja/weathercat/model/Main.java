package org.smartninja.weathercat.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blaz on 08/05/16.
 */
public class Main implements Parcelable {

    private float temp;

    public Main(float temp) {
        this.temp = temp;
    }

    protected Main(Parcel in) {
        temp = in.readFloat();
    }

    public float getTemp() {
        return temp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(temp);
    }

    public static final Creator<Main> CREATOR = new Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };

}
