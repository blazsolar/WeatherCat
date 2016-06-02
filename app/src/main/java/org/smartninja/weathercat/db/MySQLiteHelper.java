package org.smartninja.weathercat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by blaz on 02/06/16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cities.db";
    private static final int DATABASE_VERSION = 1;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table cities (_id integer primary key autoincrement, city_id integer);");


        long[] cities = new long[] {
                3239318, 3186843, 3192062, 3197378, 3194351, 3198647, 3192241, 3195506, 5128638,
                1689973, 3186886, 2759794, 5056033, 2950159, 2988507, 292223, 1609350, 1138958
        };

        for (long city : cities) {
            ContentValues values = new ContentValues();
            values.put("city_id", city);

            db.insert("cities", null, values);
        }

    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
